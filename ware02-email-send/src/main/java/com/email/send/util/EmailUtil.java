package com.email.send.util;

import com.email.send.param.EmailParam;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 邮箱发送工具类
 */
public class EmailUtil {

    public static void main(String[] args) throws Exception {
        sendEmail01("dzyaly@aliyun.com","复杂邮件","自定义图片：<img src='cid:gzh.jpg'/>,网络图片：<img src='http://pic37.nipic.com/20140113/8800276_184927469000_2.png'/>") ;
    }

    /**
     * 邮箱发送模式01：纯文本格式
     */
    public static void sendEmail01 (String receiver,String title,String body) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", EmailParam.emailHost);
        prop.setProperty("mail.transport.protocol", EmailParam.emailProtocol);
        prop.setProperty("mail.smtp.auth", EmailParam.emailAuth);
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(EmailParam.emailHost, EmailParam.emailSender, EmailParam.password);
        //4、创建邮件
        // Message message = createEmail01(session,receiver,title,body);
        Message message = createEmail01(session,receiver,title,body);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * 邮箱发送模式02：复杂格式
     */
    public static void sendEmail02 (String receiver,String title,String body) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", EmailParam.emailHost);
        prop.setProperty("mail.transport.protocol", EmailParam.emailProtocol);
        prop.setProperty("mail.smtp.auth", EmailParam.emailAuth);
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(EmailParam.emailHost, EmailParam.emailSender, EmailParam.password);
        //4、创建邮件
        // Message message = createEmail01(session,receiver,title,body);
        Message message = createEmail02(session,receiver,title,body);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * 创建文本邮件
     */
    private static MimeMessage createEmail01(Session session,String receiver,String title,String body)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        String nick = javax.mail.internet.MimeUtility.encodeText(EmailParam.emailNick);
        message.setFrom(new InternetAddress(nick+"<"+EmailParam.emailSender+">"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        //邮件的标题
        message.setSubject(title);
        //邮件的文本内容
        message.setContent(body, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }

    private static MimeMessage createEmail02 (Session session,String receiver,String title,String body)
            throws Exception{
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        String nick = javax.mail.internet.MimeUtility.encodeText(EmailParam.emailNick);
        message.setFrom(new InternetAddress(nick+"<"+EmailParam.emailSender+">"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        //邮件的标题
        message.setSubject(title);
        //文本内容
        MimeBodyPart text = new MimeBodyPart() ;
        text.setContent(body,"text/html;charset=UTF-8");
        //图片内容
        MimeBodyPart image = new MimeBodyPart();
        image.setDataHandler(new DataHandler(new FileDataSource("ware-email-send/src/gzh.jpg")));
        image.setContentID("gzh.jpg");
        //附件内容
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler file = new DataHandler(new FileDataSource("ware-email-send/src/gzh.zip"));
        attach.setDataHandler(file);
        attach.setFileName(file.getName());
        //关系:正文和图片
        MimeMultipart multipart1 = new MimeMultipart();
        multipart1.addBodyPart(text);
        multipart1.addBodyPart(image);
        multipart1.setSubType("related");
        //关系:正文和附件
        MimeMultipart multipart2 = new MimeMultipart();
        multipart2.addBodyPart(attach);
        // 全文内容
        MimeBodyPart content = new MimeBodyPart();
        content.setContent(multipart1);
        multipart2.addBodyPart(content);
        multipart2.setSubType("mixed");
        // 封装 MimeMessage 对象
        message.setContent(multipart2);
        message.saveChanges();
        // 本地查看文件格式
        message.writeTo(new FileOutputStream("F:\\MixedMail.eml"));
        //返回创建好的邮件对象
        return message;
    }

    /**
     * 文本邮箱模板
     */
    public static String convertTextModel (String body,String param1,String param2){
        return String.format(body,param1,param2) ;
    }
    /**
     * 图片邮箱模板
     */
    public static String convertImageModel (String body,String param1){
        return String.format(body,param1) ;
    }
    /**
     * 文件邮箱模板
     */
    public static String convertFileModel (String body,String param1){
        return String.format(body,param1) ;
    }
    
    
    @Autowired
    JavaMailSender javaMailSender; //邮件发送

    public void contextLoads() {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件设置值
        simpleMailMessage.setSubject("测试邮件-java邮件任务");//邮件主题
        simpleMailMessage.setText("测试邮件,测试java发送邮件任务......");//邮件内容
        simpleMailMessage.setTo("2679044670@qq.com");//邮件发给谁
        simpleMailMessage.setFrom("sysmessage@cguarantee.cn"); //邮件来自于谁
        javaMailSender.send(simpleMailMessage);
    }



    /**
     * 复杂邮件
     */
    public void contextLoads2(EmailBean t) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //防止判断为垃圾邮箱
        mimeMessage.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        String[] addressee = t.getAddresseeList().toArray(new String[t.getAddresseeList().size()]);
        mimeMessageHelper.setTo(addressee);//邮件发给谁
//			mimeMessageHelper.setFrom("417511524@qq.com");
        mimeMessageHelper.setFrom("sysmessage@cguarantee.cn");//邮件来自于谁
        mimeMessageHelper.setSubject(t.getTitle());//邮件主题
        mimeMessageHelper.setText(t.getContent(), false);//邮件内容
        javaMailSender.send(mimeMessage);
    }
    
    
     public void contextLoads2() throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        //邮件设置值
        mimeMessageHelper.setSubject("测试邮件-java邮件任务(复杂邮件)");//邮件主题
        mimeMessageHelper.setText("<b style='color:red'>测试邮件,测试java发送邮件任务......</b>",true);//邮件内容
        //邮件 附件
       // mimeMessageHelper.addAttachment("ceshi1.jpg",new File("C:\\Users\\57132\\Desktop\\ceshi1.png"));
       // mimeMessageHelper.addAttachment("ceshi2.jpg",new File("C:\\Users\\57132\\Desktop\\ceshi2.png"));
        mimeMessageHelper.setTo("2679044670@qq.com");//邮件发给谁
        mimeMessageHelper.setFrom("sysmessage@cguarantee.cn"); //邮件来自于谁
        javaMailSender.send(mimeMessage);
    }

}
