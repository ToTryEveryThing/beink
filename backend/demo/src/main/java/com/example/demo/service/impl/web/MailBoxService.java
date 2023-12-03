
/***
 * @author 睡醒继续做梦
 * @date 2023/3/1 17:07
 */


@Service
public class MailBindServiceImpl implements MailBindService {

    @Autowired
    private redisUtil redisUtil;

    @Autowired
    private WebMapper webMapper;

    @Override
    public ApiResponse<Void> bind(String account, String mail, String code) {

        System.out.println(redisUtil.get(mail));
        System.out.println(code);
        if(redisUtil.get(mail)==null || !redisUtil.get(mail).equals(code))
            return ApiResponse.error(0,"邮箱验证码有误");

        QueryWrapper<web> q = new QueryWrapper<>();
        q.eq("account",account);
        web web = new web();
        web.setMail(mail);
        int update = webMapper.update(web, q);
        System.out.println(update);
        if(update>=1)return ApiResponse.success();
        return ApiResponse.error(0,"邮箱验证码有误");
    }
}
