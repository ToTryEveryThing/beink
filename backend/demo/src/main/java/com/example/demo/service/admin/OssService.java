package com.example.demo.service.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.AliossConfig;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.user.WebMapper;
import com.example.demo.pojo.user.web;
import com.example.demo.utils.redisUtil;
import com.example.demo.pojo.OssTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.example.demo.constants.radis.redisConstants.*;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */
@Service
public class OssService {

    @Autowired
    private redisUtil redisUtil;

    @Autowired
    private WebMapper webMapper;


    @Autowired
    private AliossConfig aliossConfig;


    public ApiResponse<Set<Object>> uploadObject(MultipartFile file, String keyPrefix) throws IOException {
        String objectName = keyPrefix + "/";
        String originalFilename = file.getOriginalFilename();
        OSS  os = new OSSClientBuilder().build(
                aliossConfig.getEndpoint(),
                aliossConfig.getAccessKeyId(),
                aliossConfig.getAccessKeySecert());
        os.putObject(
                aliossConfig.getBucket(),
                objectName + originalFilename,
//                originalFilename,
                file.getInputStream()
        );
        os.shutdown();
        redisUtil.sSet(REDIS_OSS + keyPrefix, objectName + originalFilename);
        return ApiResponse.success(redisUtil.sGet(REDIS_OSS + keyPrefix));

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

    public void getList(String keyPrefix){
        OSS ossClient = new OSSClientBuilder().build(aliossConfig.getEndpoint()
                ,aliossConfig.getAccessKeyId()
                ,aliossConfig.getAccessKeySecert());
        try {
            // 列举文件。如果不设置keyPrefix，则列举存储空间下的所有文件。如果设置keyPrefix，则列举包含指定前缀的文件。
            ObjectListing objectListing = ossClient.listObjects(aliossConfig.getBucket(),keyPrefix);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            ArrayList<String> list = new ArrayList<>();
            for (OSSObjectSummary s : sums) {
//                System.out.println("\t" + s.getKey());
//                list.add(s.getKey());
                redisUtil.sSet(REDIS_OSS + keyPrefix, s.getKey());
            }
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
    }

    public ApiResponse<Set<Object>> redisList(String keyPrefix){
        System.out.println("!!!!!!!!!!!!!!!!!!!");
        if(redisUtil.hasKey(REDIS_OSS + keyPrefix))
            return ApiResponse.success(redisUtil.sGet(REDIS_OSS + keyPrefix));
        System.out.println("???????????????????");
        this.getList(keyPrefix);
        return ApiResponse.success(redisUtil.sGet(REDIS_OSS + keyPrefix));
    }

    public ApiResponse<Set<Object>> deleteObject(String s, String keyPrefix){
        OSS ossClient = new OSSClientBuilder().build(
                 aliossConfig.getEndpoint()
                ,aliossConfig.getAccessKeyId()
                ,aliossConfig.getAccessKeySecert());
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
//            删除的返回值是什么？
//            不太安全
            ossClient.deleteObject(aliossConfig.getBucket(), s);
            long l = redisUtil.setRemove(REDIS_OSS + keyPrefix, s);
            return ApiResponse.success(redisUtil.sGet(REDIS_OSS + keyPrefix));
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
        return ApiResponse.error(0,"删除失败");
    }

    public ApiResponse userupload(MultipartFile file, String keyPrefix, String name) throws IOException {

        // TODO 限制大小 重命名 限制格式
        String objectName = keyPrefix + "/";
        String originalFilename = name + "_" + file.getOriginalFilename();
        OSS  os = new OSSClientBuilder().build(
                aliossConfig.getEndpoint(),
                aliossConfig.getAccessKeyId(),
                aliossConfig.getAccessKeySecert());
        os.putObject(
                aliossConfig.getBucket(),
                objectName + originalFilename,
                file.getInputStream()
        );
        os.shutdown();

        redisUtil.hset(REDIS_OSS_USER, name, objectName + originalFilename);

        QueryWrapper<web> q = new QueryWrapper<>();
        q.eq("account",name);
        web web = new web();
        web.setUserimage(objectName + originalFilename);
        webMapper.update(web,q);
        return ApiResponse.success(objectName + originalFilename);
    }

    /**
     * 后端签名
     */
    public ApiResponse<Map<String, String>> policyyyy()  {

        String host = "https://" + aliossConfig.getBucket() + "." + "oss-cn-beijing.aliyuncs.com";
        String dir = "background/";

        OSS ossClient = new OSSClientBuilder().build(
                aliossConfig.getEndpoint()
                ,aliossConfig.getAccessKeyId()
                ,aliossConfig.getAccessKeySecert());

        long expireTime = 6000;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData;
        binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        Map<String, String> respMap = new LinkedHashMap<>();
        respMap.put("OSSAccessKeyId", aliossConfig.getAccessKeyId());
        respMap.put("policy", encodedPolicy);
        respMap.put("Signature", postSignature);
        respMap.put("dir", dir);
        respMap.put("host", host);
        respMap.put("expire", String.valueOf(expireEndTime / 1000));

        return ApiResponse.success(respMap);
    }

}
