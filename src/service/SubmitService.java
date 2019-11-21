package service;

/**
 * 表单上传服务接口
 */
public interface SubmitService {
    /**
     * 提交（新增修改）处理，包含文件上传
     * @param companyId 企业Id
     * @return true-提交成功 false-提交失败
     */
    public  Long doSubmit(String companyId);
}
