package com.example.demo.service.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.config.AliossConfig;
import com.example.demo.controller.common.Result;
import com.example.demo.utils.redisUtil;
import com.example.demo.pojo.OssTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */

@Service
public class OssService {

    @Autowired
    private redisUtil redisUtil;


    @Autowired
    private AliossConfig aliossConfig;

    public Result uploadObject(MultipartFile file) throws IOException {
        String objectName = "background/";
        String originalFilename = file.getOriginalFilename();
        OSS  os = new OSSClientBuilder().build(
                aliossConfig.getEndpoint(),
                aliossConfig.getAccessKeyId(),
                aliossConfig.getAccessKeySecert());
        os.putObject(
                aliossConfig.getBucket(),
//                "background/" + originalFilename,
                originalFilename,
                file.getInputStream()
        );
        os.shutdown();
        return new Result(1,"success",getList());

    }

    public OssTokenVo getToken() throws ClientException {

        DefaultProfile profile = DefaultProfile.getProfile(aliossConfig.getRegionID(),
                aliossConfig.getAccessKeyId(),
                aliossConfig.getAccessKeySecert()
        );
        DefaultAcsClient client = new DefaultAcsClient(profile);
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleSessionName("beink");
        request.setRoleArn(aliossConfig.getRoleArn());
        request.setDurationSeconds(1000L);
        AssumeRoleResponse acsResponse = client.getAcsResponse(request);

        //拿到前端需要的数据了
        AssumeRoleResponse.Credentials credentials = acsResponse.getCredentials();
        String accessKeyId = credentials.getAccessKeyId();
        String securityToken = credentials.getSecurityToken();
        String accessKeySecret = credentials.getAccessKeySecret();

        //构建可能需要的vo
       return OssTokenVo.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecert(accessKeySecret)
                .stsToken(securityToken)
                .region("oss-" +  aliossConfig.getRegionID())
                .bucket(aliossConfig.getBucket())
                .build();
    }

    public List getList(){
        System.out.println("我是一");
        OSS ossClient = new OSSClientBuilder().build(aliossConfig.getEndpoint()
                ,aliossConfig.getAccessKeyId()
                ,aliossConfig.getAccessKeySecert());

        try {
            // 列举文件。如果不设置keyPrefix，则列举存储空间下的所有文件。如果设置keyPrefix，则列举包含指定前缀的文件。
            ObjectListing objectListing = ossClient.listObjects(aliossConfig.getBucket());
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            ArrayList<String> list = new ArrayList<>();
            for (OSSObjectSummary s : sums) {
//                System.out.println("\t" + s.getKey());
                list.add(s.getKey());
            }
            redisUtil.set("oss",list);
            return list;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (Exception e){
          e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    public Result redisList(){
        if(redisUtil.get("oss")==null){
            redisUtil.set("oss",this.getList());
        }
        return new Result(1,"success",redisUtil.get("oss"));

    }

    public Result deleteObject(String s){
        OSS ossClient = new OSSClientBuilder().build(
                 aliossConfig.getEndpoint()
                ,aliossConfig.getAccessKeyId()
                ,aliossConfig.getAccessKeySecert());
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(aliossConfig.getBucket(), s);
            return new Result(1,"success",getList());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return new Result(0,"error");
    }
}
