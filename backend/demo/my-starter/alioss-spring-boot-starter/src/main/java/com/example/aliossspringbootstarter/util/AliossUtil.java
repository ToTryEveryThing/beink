package com.example.aliossspringbootstarter.util;


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
import com.example.aliossspringbootstarter.domain.properties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author ToTryEveryThing
 * @date 2023/12/14 9:13
 * @Description
 */
@Data
@AllArgsConstructor
public class AliossUtil {

    private properties properties;

    /**
     * 上传文件
     * @param file
     * @param keyPrefix
     * @return 路径
     * @throws IOException
     */
    public String uploadObject(MultipartFile file, String keyPrefix) throws IOException {
        String objectName = keyPrefix + "/";
        String originalFilename = file.getOriginalFilename();
        OSS os = new OSSClientBuilder().build(
                properties.getEndpoint(),
                properties.getAccessKeyId(),
                properties.getAccessKeySecert());
        os.putObject(
                properties.getBucket(),
                objectName + originalFilename,
//                originalFilename,
                file.getInputStream()
        );
        os.shutdown();
        return objectName + originalFilename;
    }


    /**
     *  根据前缀 拿文件列表
     * @param keyPrefix
     */
    public ArrayList<String> getList(String keyPrefix){
        OSS ossClient = new OSSClientBuilder().build(properties.getEndpoint()
                ,properties.getAccessKeyId()
                ,properties.getAccessKeySecert());
        try {
            // 列举文件。如果不设置keyPrefix，则列举存储空间下的所有文件。如果设置keyPrefix，则列举包含指定前缀的文件。
            ObjectListing objectListing = ossClient.listObjects(properties.getBucket(),keyPrefix);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            ArrayList<String> list = new ArrayList<>();
            for (OSSObjectSummary s : sums) {
//                System.out.println("\t" + s.getKey());
                list.add(s.getKey());
            }
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


    /**
     * 删除文件
     * @param s
     * @param keyPrefix
     * @return
     */
    public String deleteObject(String name, String keyPrefix){
        OSS ossClient = new OSSClientBuilder().build(
                properties.getEndpoint()
                ,properties.getAccessKeyId()
                ,properties.getAccessKeySecert());
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
//            删除的返回值是什么？
//            不太安全
            ossClient.deleteObject(properties.getBucket(), keyPrefix + "/" + name);
            return "ok";
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
        return "fail";
    }


    /**
     * 后端签名，前端上传
     * @return 密钥
     */
    public Map<String, String> policyyyy()  {

        String host = "https://" + properties.getBucket() + "." + "oss-cn-beijing.aliyuncs.com";
        String dir = "background/";

        OSS ossClient = new OSSClientBuilder().build(
                properties.getEndpoint()
                ,properties.getAccessKeyId()
                ,properties.getAccessKeySecert());

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
        respMap.put("OSSAccessKeyId", properties.getAccessKeyId());
        respMap.put("policy", encodedPolicy);
        respMap.put("Signature", postSignature);
        respMap.put("dir", dir);
        respMap.put("host", host);
        respMap.put("expire", String.valueOf(expireEndTime / 1000));

        return respMap;
    }
    
}
