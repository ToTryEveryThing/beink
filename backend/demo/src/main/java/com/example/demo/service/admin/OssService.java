package com.example.demo.service.admin;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.config.AliossConfig;
import com.example.demo.vo.OssTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 睡醒继续做梦
 * @date 2022/12/20
 */

@Service
public class OssService {

    @Autowired
    private AliossConfig aliossConfig;

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
}
