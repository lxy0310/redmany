package dao;

import common.SQLHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BackLoginDao extends BaseDao {
    public BackLoginDao (SQLHelper pSQLHelper){ super(pSQLHelper);}

    /**
     * 企业是否过期
     * @param companyId
     * @return
     */
    public int getExpireTimeByCompanyId(String companyId,String Company){
        int result=0;
        String sql="select expire_time from company_b where company_code=?";
        String[] parameters={Company};
        Date CompanyDate=(Date) sqlHelper.ExecScalar(companyId,sql,parameters);
        Date nowDate=new Date();
        if (CompanyDate.getTime() >= nowDate.getTime()){
            result = 1;
        }
        return result;
    }

    /**
     * 查询企业是否存在
     * @param CompanyId
     * @return
     */
    public int getcheckCompanyId(String CompanyId,String Company){
        String sql="select COUNT(*) from company_b where company_code=?";
        String[] parameters={Company};
        System.out.println(sql);
        return sqlHelper.ExecScalar(CompanyId,sql,parameters)!=null?(int)sqlHelper.ExecScalar(CompanyId,sql,parameters):0;
    }

    /**
     * 查询企业手机端登录数量
     * @param companyId
     * @param Company
     * @return
     */
    public int getUsersMobileNum(String companyId,String Company){
        String sql="select users_mobile from company_b where company_code=?";
        String[] parameters={Company};
        String str=sqlHelper.ExecScalar(companyId,sql,parameters)!=null?(String) sqlHelper.ExecScalar(companyId,sql,parameters):null;
        int result=0;
        if (str!=null){
            result=Integer.valueOf(str);
        }
        return result;
    }

    /**
     * 查询企业PC端登录数量
     * @param companyId
     * @param Company
     * @return
     */
    public int getUsersPCNum(String companyId,String Company){
        String sql="select users_computer from company_b where company_code=?";
        String[] parameters={Company};
        String str=sqlHelper.ExecScalar(companyId,sql,parameters)!=null?(String) sqlHelper.ExecScalar(companyId,sql,parameters):null;
        int result=0;
        if (str!=null){
            result=Integer.valueOf(str);
        }
        return result;
    }

    /**
     * 查询企业 是否过期  手机端登录数量  PC端登录数量
     * @param CompanyId
     * @return
     */
    public List<Map<String,Object>> getcheckCompanys(String CompanyId,String Company){
        String sql="select expire_time,users_mobile,users_computer from company_b where company_code=?";
        String[] parameters={Company};
        System.out.println(sql);
        return sqlHelper.executeQueryList(CompanyId,sql,parameters)!=null?sqlHelper.executeQueryList(CompanyId,sql,parameters):null;
    }

    /**
     * 手机登录人数
     * @param Company
     * @return
     */
    public int getLoginMobileNum(String Company){
        String sql="SELECT count(*) from [user] where status=1";
        Integer str=sqlHelper.ExecScalar(Company,sql,null)!=null?(Integer) sqlHelper.ExecScalar(Company,sql,null):0;
        int result=0;
        if (str!=null){
            result=Integer.valueOf(str);
        }
        return result;
    }

    /**
     * PC登录人数
     * @param Company
     * @return
     */
    public int getLoginPCNum(String Company){
        String sql="SELECT count(*) from [user] where status=0";
        Integer str=sqlHelper.ExecScalar(Company,sql,null)!=null?(Integer) sqlHelper.ExecScalar(Company,sql,null):null;
        int result=0;
        if (str!=null){
            result=Integer.valueOf(str);
        }
        return result;
    }

}
