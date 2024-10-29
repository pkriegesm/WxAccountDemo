# WxAccountDemo
Integration Server Package in the context of Super iPaaS Demo.

## webMethods Cloud Configuration

This package can create/update a "webMethods Cloud Application", including the needed Tenant connection and Account. This is done inside an startup service using the **Global (Package) Variables** listed below (note that, due to the internal/admin services used, the variables named with *"account"* are used to configure the tenant connection, while the variable named with *"connection"* is used to configure the *"account"*...)

- ***wx.account.demo.cloud.app.reload***

    Possible values are true/false. If it does not exist or its value is false, no application will be created/updated

- ***wx.account.demo.cloud.account.aliasName***

    Name of the alias used when creating a **Tenant Connection**

- ***wx.account.demo.cloud.account.tenantURL***

    URL of the IBM webMethods Integration Tenant to be used for this connection

- ***wx.account.demo.cloud.account.user***

    User of the IBM webMethods Integration Tenant to be used for this connection

- ***wx.account.demo.cloud.account.pwd***

    Password of the IBM webMethods Integration Tenant to be used for this connection user

- ***wx.account.demo.cloud.connection.aliasName***

    Alias name to create an **(On-Premise) Account** to be used by the Cloud Application when synchronized to the Tenant defined previously in the connection.

- ***wx.account.demo.cloud.app.services***

    Comma separated list of the full qualified services to be included in the application and *"uploaded"* to the configured tenant (you can see an example below)

These properties can be defined using a configuration variables template like:

```
    globalvariable.WxAccountDemo.wx..account..demo..cloud..account..aliasName.value = <YOUR_ALIAS>

    globalvariable.WxAccountDemo.wx..account..demo..cloud..account..pwd.isSecure = true

    globalvariable.WxAccountDemo.wx..account..demo..cloud..account..pwd.value = <YOUR_PASSWORD>

    globalvariable.WxAccountDemo.wx..account..demo..cloud..account..tenantURL.value = https://your-tenant.webmethods.io

    globalvariable.WxAccountDemo.wx..account..demo..cloud..account..user.value = <YOUR_USER>

    globalvariable.WxAccountDemo.wx..account..demo..cloud..app..services.value = wx.accountdemo.pub.services\:createCustomerAccounts,wx.accountdemo.pub.services\:deleteAccountByID,wx.accountdemo.pub.services\:getAllCustomerAccounts,wx.accountdemo.pub.services\:getCustomerAccountByID

    globalvariable.WxAccountDemo.wx..account..demo..cloud..connection..aliasName.value = <YOUR_HYBRID_USER>
```