package com.facishare.login; /**
 * Created by sunsk on 2017/8/3.
 */
import com.alibaba.fastjson.annotation.JSONField;

//@Data
public class loginBean {
    private String EnterpriseAccount;

    @JSONField(name = "EnterpriseAccount")
    public String getEnterpriseAccount() {
        return EnterpriseAccount;
    }

    public void setEnterpriseAccount(String EnterpriseAccount) {
        this.EnterpriseAccount = EnterpriseAccount;
    }
//    @JSONField(name = "EnterpriseAccount",serialize = false)
//    public String EnterpriseAccount="";


    private String UserAccount;

    @JSONField(name = "UserAccount")
    public String getUserAccount() {
        return UserAccount;
    }

    public void setUserAccount(String UserAccount) {
        this.UserAccount = UserAccount;
    }

    private String Password="";
    @JSONField(name = "Password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }


    private String ImgCode="";
    @JSONField(name = "ImgCode")
    public String getImgCode() {
        return ImgCode;
    }

    public void setImgCode(String ImgCode) {
        this.ImgCode = ImgCode;
    }
    @JSONField(name = "ClientId")
    private String ClientId="";
    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }
    @JSONField(name = "PersistenceHint")
    private String PersistenceHint;
    public String getPersistenceHint() {
        return PersistenceHint;
    }

    public void setPersistenceHint(String PersistenceHint) {
        this.PersistenceHint = PersistenceHint;
    }
}
