//package com.larkEWA.utils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lark.oapi.Client;
//import com.lark.oapi.core.utils.Jsons;
//import com.lark.oapi.okhttp.OkHttpClient;
//import com.lark.oapi.okhttp.Request;
//import com.lark.oapi.okhttp.RequestBody;
//import com.lark.oapi.okhttp.Response;
//import com.lark.oapi.okhttp.MediaType;
//import com.lark.oapi.service.attendance.v1.model.*;
//import com.lark.oapi.service.contact.v3.model.*;
//import com.lark.oapi.service.contact.v3.model.User;
//
//
//import java.io.IOException;
//import java.text.NumberFormat;
//import java.util.*;
//
//import com.lark.oapi.core.request.RequestOptions;
//import com.lark.oapi.service.authen.v1.model.*;
//
//import com.lark.oapi.service.tenant.v2.model.QueryTenantResp;
//import com.larkEWA.dto.attendance.AttendanceResultDto;
//import com.larkEWA.dto.larkAccount.*;
//
//import com.larkEWA.dto.staff.CreateStaffDto;
//import com.larkEWA.enums.StatusCode;
//import com.larkEWA.exception.AppException;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LarkUtil {
//    @Value("${lark.appId}")
//    private String appId;
//
//    @Value("${lark.appSecret}")
//    private String appSecret;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public LinkDto getLink() {
//        String url = "https://open.larksuite.com/open-apis/authen/v1/authorize?";
//        String redirectUri = "http://localhost:3000";
//        String scope = "contact:contact";
//        String link = String.format("%sapp_id=%s&redirect_uri=%s&scope=%s&state=RANDOMSTATE", url, appId,redirectUri,scope);
//        return LinkDto.builder().link(link).build();
//    }
//
//    public TenantAccessTokenDto getTenantAccessToken() throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        String reqBody = "{\n" +
//                "  \"app_id\": \"" + appId + "\",\n" +
//                "  \"app_secret\": \"" + appSecret + "\"\n" +
//                "}";
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType,reqBody );
//        Request request = new Request.Builder()
//                .url("https://open.larksuite.com/open-apis/auth/v3/tenant_access_token/internal")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//
//        if(!response.isSuccessful()) {
//            return null;
//        }
//
//        String responseBody = response.body().string();
//
//        TenantAccessTokenDto tenantAccessTokenDto = objectMapper.readValue(responseBody, TenantAccessTokenDto.class);
//
//        System.out.println(tenantAccessTokenDto.getTenant_access_token());
//
//        // Chuyển đổi JSON thành đối tượng GrantTokenDto
//        return tenantAccessTokenDto;
//    }
//
//    public AccessTokenDto getUserAccessToken(String code) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        // 创建请求对象
//        CreateOidcAccessTokenReq req = CreateOidcAccessTokenReq.newBuilder()
//                .createOidcAccessTokenReqBody(CreateOidcAccessTokenReqBody.newBuilder()
//                        .grantType("authorization_code")
//                        .code(code)
//                        .build())
//                .build();
//
//        // 发起请求
//        CreateOidcAccessTokenResp resp = client.authen().oidcAccessToken().create(req);
//
//        // 处理服务端错误
//        if(!resp.success()) {
//            throw new AppException(StatusCode.FAIL_GET_LARK_USER_TOKEN);
//        }
//
//        // 业务数据处理
//        return modelMapper.map(resp.getData(), AccessTokenDto.class);
//    }
//
//    public AccessTokenDto refreshUserAccessToken(String refreshToken) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        // 创建请求对象
//        CreateOidcRefreshAccessTokenReq req = CreateOidcRefreshAccessTokenReq.newBuilder()
//                .createOidcRefreshAccessTokenReqBody(CreateOidcRefreshAccessTokenReqBody.newBuilder()
//                        .grantType("refresh_token")
//                        .refreshToken(refreshToken)
//                        .build())
//                .build();
//
//        // 发起请求
//        CreateOidcRefreshAccessTokenResp resp = client.authen().oidcRefreshAccessToken().create(req);
//
//        // 处理服务端错误
//        if(!resp.success()) {
//            throw new AppException(StatusCode.FAIL_REFRESH_USER_TOKEN);
//        }
//
//        // 业务数据处理
//        return modelMapper.map(resp.getData(), AccessTokenDto.class);
//    }
//
//    public LarkUserDto getUserInfo(String accessToken) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        // 创建请求对象
//        // 发起请求
//        GetUserInfoResp resp = client.authen().userInfo().get(RequestOptions.newBuilder()
//                .userAccessToken(accessToken)
//                .build());
//
//        // 处理服务端错误
//        if(!resp.success()) {
//            throw new AppException(StatusCode.FAIL_GET_USER_INFO);
//        }
//
//        // 业务数据处理
//        return modelMapper.map(resp.getData(), LarkUserDto.class);
//    }
//
//    public List<String> getSubDepartmentIds(String parentDepartmentId, String tenantAccessToken) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        // 创建请求对象
//        ChildrenDepartmentReq req = ChildrenDepartmentReq.newBuilder()
//                .departmentId(parentDepartmentId)
//                .userIdType("open_id")
//                .departmentIdType("department_id")
//                .pageSize(30)
//                .build();
//
//        // 发起请求
//        ChildrenDepartmentResp resp = client.contact().department().children(req, RequestOptions.newBuilder()
//                .tenantAccessToken(tenantAccessToken)
//                .build());
//
//        // 处理服务端错误
//        if(!resp.success()) {
//            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
//            throw new AppException(StatusCode.FAIL_GET_SUB_DEPARTMENT);
//        }
//
//        // 业务数据处理
//        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
//
//        return resp.getData().getItems()!=null ? Arrays.stream(resp.getData().getItems()).map(
//                Department::getDepartmentId
//        ).toList() :  new ArrayList<>();
//    }
//
//    public List<CreateStaffDto> getUsers(String tenantAccessToken, String openId) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        List<CreateStaffDto> staffs = new ArrayList<>();
//
//        Queue<String> departmentQueue = new LinkedList<>();
//
//        departmentQueue.add("0");
//
//        List<String> openIds = new ArrayList<>();
//
//        while(!departmentQueue.isEmpty()){
//            String front = departmentQueue.remove();
//            System.out.println(front);
//
//            List<String> subDepartmentIds = getSubDepartmentIds(front, tenantAccessToken);
//
//            departmentQueue.addAll(subDepartmentIds);
//
//            // 创建请求对象
//            FindByDepartmentUserReq req = FindByDepartmentUserReq.newBuilder()
//                    .departmentIdType("department_id")
//                    .departmentId(front)
//                    .build();
//
//
//            RequestOptions requestOptions = RequestOptions.newBuilder()
//                    .tenantAccessToken(tenantAccessToken)
//                    .build();
//
//            // 发起请求
//            FindByDepartmentUserResp resp = client.contact().user().findByDepartment(req, requestOptions);
//
//            // 处理服务端错误
//            if(!resp.success()) {
//                System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
//                throw new AppException(StatusCode.FAIL_GET_STAFF);
//            }
//
//            // 业务数据处理
//            User[] users = resp.getData().getItems();
//
//            final boolean[] hasAdmin = {false};
//
//            Arrays.stream(users).forEach(
//                    user ->{
//                        if(!user.getIsTenantManager() && Objects.equals(user.getOpenId(), openId)){
//                            System.out.println(user.getIsTenantManager());
//                            System.out.println(user.getEmail());
//                            throw new AppException(StatusCode.LARK_NOT_ADMIN);
//                        }
//                        if(!openIds.contains(user.getOpenId())){
//                            staffs.add(CreateStaffDto.builder()
//                                    .staffId(user.getEmployeeNo())
//                                    .email(user.getEmail())
//                                    .openId(user.getOpenId())
//                                    .name(user.getName())
//                                    .larkUserId(user.getUserId())
//                                    .avatar(user.getAvatar().getAvatar240())
//                                    .build());
//                            openIds.add(user.getOpenId());
//                            System.out.println(Jsons.DEFAULT.toJson(openIds));
//                        }
//                    }
//            );
//        }
//
//        return staffs;
//    }
//
//    public void modifyUserInfo(String tenantAccessToken, String userId, String employeeNo, String name) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        // 创建请求对象
//        PatchUserReq req = PatchUserReq.newBuilder()
//                .userId(userId)
//                .userIdType("user_id")
//                .departmentIdType("open_department_id")
//                .user(User.newBuilder()
//                        .employeeNo(employeeNo)
//                        .name(name)
//                        .build())
//                .build();
//
//        // 发起请求
//        PatchUserResp resp = client.contact().user().patch(req, RequestOptions.newBuilder().tenantAccessToken(tenantAccessToken).build());
//
//        // 处理服务端错误
//        if(!resp.success()) {
//            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
//            throw new AppException(StatusCode.FAIL_UPDATE_STAFF);
//        }
//
//        // 业务数据处理
//        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
//    }
//
//    public String getCompanyInfo(String tenantAccessToken) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        // 创建请求对象
//        RequestOptions options = RequestOptions.newBuilder().tenantAccessToken(tenantAccessToken).build();
//
//        // 发起请求
//        QueryTenantResp resp = client.tenant().tenant().query(options);
//
//        // 处理服务端错误
//        if(!resp.success()) {
//            throw new AppException(StatusCode.FAIL_GET_COMPANY_INFO);
//        }
//
//        // 业务数据处理
//        return resp.getData().getTenant().getTenantKey();
//    }
//
//    public String createSalaryApprovalInstance(String tenantAccessToken, String approvalCode, String openId, int amount, String reason) throws Exception {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
//        String amountStr = numberFormat.format(amount);
//        if(reason==null  || reason.isEmpty()){
//            reason = "Không có lí do";
//        }
//
//        RequestBody body = RequestBody.create(mediaType, "{\"approval_code\":\""+approvalCode+"\",\"form\":\"[{\\\"id\\\":\\\"111\\\",\\\"type\\\":\\\"input\\\",\\\"value\\\":\\\""+amountStr+"\\\",\\\"required\\\":true},{\\\"id\\\":\\\"222\\\",\\\"type\\\":\\\"textarea\\\",\\\"value\\\":\\\""+reason+"\\\",\\\"required\\\":true}]\",\"open_id\":\""+openId+"\"}");
//        Request request = new Request.Builder()
//                .url("https://open.larksuite.com/open-apis/approval/v4/instances")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer "+tenantAccessToken)
//                .build();
//        Response response = client.newCall(request).execute();
//
//        if(!response.isSuccessful()){
//            System.out.println(response);
//            throw new AppException(StatusCode.FAIL_CREATE_APPROVAL_INSTANCE);
//        }
//
//        String responseBody = response.body().string();
//
//        System.out.println(responseBody);
//
//        AppInsResDto res = objectMapper.readValue(responseBody, AppInsResDto.class);
//
//        return res.getData().getInstance_code();
//    }
//
//    public String createSalaryApprovalDefinition(String openId, String tenantAccessToken) throws Exception {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"approval_name\":\"@i18n@Salary_Advance_Request\",\"config\":{\"can_update_form\":true,\"can_update_process\":true,\"can_update_revert\":true,\"can_update_viewer\":false,\"help_url\":\"https://www.baidu.com\"},\"form\":{\"form_content\":\"[{\\\"id\\\":\\\"111\\\",\\\"name\\\":\\\"@i18n@amount\\\",\\\"required\\\":true,\\\"type\\\":\\\"input\\\"},{\\\"id\\\":\\\"222\\\",\\\"name\\\":\\\"@i18n@reason\\\",\\\"required\\\":true,\\\"type\\\":\\\"textarea\\\"}]\"},\"i18n_resources\":[{\"is_default\":true,\"locale\":\"en-US\",\"texts\":[{\"key\":\"@i18n@Salary_Advance_Request\",\"value\":\"Salary_Advance_Request\"},{\"key\":\"@i18n@node_name\",\"value\":\"node_name\"},{\"key\":\"@i18n@amount\",\"value\":\"amount\"},{\"key\":\"@i18n@reason\",\"value\":\"reason\"}]}],\"icon\":1,\"node_list\":[{\"id\":\"START\",\"privilege_field\":{\"readable\":[\"111\",\"222\"],\"writable\":[\"111\",\"222\"]}},{\"approver\":[{\"type\":\"Personal\",\"user_id\":\""+openId+"\"}],\"ccer\":[{\"level\":\"2\",\"type\":\"Supervisor\"}],\"id\":\"7106864726566\",\"name\":\"@i18n@node_name\",\"node_type\":\"AND\",\"privilege_field\":{\"readable\":[\"111\",\"222\"],\"writable\":[\"111\",\"222\"]}},{\"id\":\"END\"}],\"process_manager_ids\":[\""+openId+"\"],\"settings\":{\"revert_interval\":0},\"viewers\":[{\"viewer_type\":\"TENANT\",\"viewer_user_id\":\"\"}]}");
//        Request request = new Request.Builder()
//                .url("https://open.larksuite.com/open-apis/approval/v4/approvals")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer "+tenantAccessToken)
//                .build();
//        Response response = client.newCall(request).execute();
//
//        if(!response.isSuccessful()){
//            System.out.println(response);
//            throw new AppException(StatusCode.FAIL_CREATE_APPROVAL_DEFINITION);
//        }
//
//        String responseBody = response.body().string();
//
//        AppDefResDto res = objectMapper.readValue(responseBody, AppDefResDto.class);
//
//        System.out.println(res.getData().getApproval_code());
//
//        // Chuyển đổi JSON thành đối tượng GrantTokenDto
//        return res.getData().getApproval_code();
//    }
//
//    public void subscribeSalaryApproval(String tenantAccessToken, String approvalCode) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url("https://open.larksuite.com/open-apis/approval/v4/approvals/"+approvalCode+"/subscribe")
//                .method("POST", null)
//                .addHeader("Authorization", "Bearer "+tenantAccessToken)
//                .build();
//        Response response = client.newCall(request).execute();
//
//        if(!response.isSuccessful()){
//            System.out.println(response);
//            throw new AppException(StatusCode.FAIL_SUBSCRIBE_APPROVAL);
//        }
//
//        System.out.println(response.body().string());
//    }
//
//    public List<AttendanceResultDto> getAttendanceResult(int startDate, int endDate, String []ids, String tenantAccessToken) throws Exception {
//        // 构建client
//        Client client = Client.newBuilder(appId, appSecret).build();
//
//        // 创建请求对象
//        QueryUserTaskReq req = QueryUserTaskReq.newBuilder()
//                .employeeType("employee_id")
//                .queryUserTaskReqBody(QueryUserTaskReqBody.newBuilder()
//                        .userIds(ids)
//                        .checkDateFrom(startDate)
//                        .checkDateTo(endDate)
//                        .build())
//                .build();
//
//        RequestOptions options = RequestOptions.newBuilder().tenantAccessToken(tenantAccessToken).build();
//
//        // 发起请求
//        QueryUserTaskResp resp = client.attendance().userTask().query(req,options);
//
//        // 处理服务端错误
//        if(!resp.success()) {
//            throw new AppException(StatusCode.FAIL_GET_ATTENDANCE);
//        }
//
//        UserTask[] userTasks = resp.getData().getUserTaskResults();
//
//        // 业务数据处理
//        System.out.println(Jsons.DEFAULT.toJson(resp.getData().getUserTaskResults()));
//
//        return Arrays.stream(userTasks).map(
//                userTask -> {
//                    TaskResult taskResult = userTask.getRecords()[0];
//                    UserFlow checkInRecord = taskResult.getCheckInRecord();
//                    UserFlow checkOutRecord = taskResult.getCheckOutRecord();
//                    return AttendanceResultDto.builder()
//                            .day(userTask.getDay())
//                            .checkIn(checkInRecord != null ? checkInRecord.getCheckTime() : null)
//                            .checkOut(checkOutRecord != null ? checkOutRecord.getCheckTime() : null)
//                            .userId(userTask.getUserId())
//                            .build();
//                }
//        ).toList();
//    }
//}
