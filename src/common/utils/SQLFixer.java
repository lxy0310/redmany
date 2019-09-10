package common.utils;

public class SQLFixer {

    /**
     * 修正sql
     *
     * @param sql
     * @return
     */
    public static String fixOrder(String sql) {
        int index = sql.lastIndexOf("order by");
        if (index >= 0) {
            if (sql.indexOf("where", index + 1) >= 0) {
                return swapWhere(sql);
            } else if (sql.indexOf("and", index + 1) >= 0) {
                return swapAnd(sql);
            } else if (sql.indexOf("or", index + 1) >= 0) {
                return swapOr(sql);
            }
        }
        return sql;
    }

    private static String swapWhere(String sql) {
        int index = sql.lastIndexOf("order by");
        if (index >= 0) {
            String pre = sql.substring(0, index);
            String last = sql.substring(index);//order by id where id = 3
            int where = last.indexOf("where");
            sql = pre;
            if (where >= 0) {
                //       where id = 3                    order by id
                sql += last.substring(where) + " " + last.substring(0, where);
            } else {
                sql += last;
            }
            return sql;
        }
        return sql;
    }

    private static String swapAnd(String sql) {
        int index = sql.lastIndexOf("order by");
        if (index >= 0) {
            String pre = sql.substring(0, index);
            String last = sql.substring(index);//order by id where id = 3
            int where = last.indexOf("and");
            sql = pre;
            if (where >= 0) {
                //       where id = 3                    order by id
                sql += last.substring(where) + " " + last.substring(0, where);
            } else {
                sql += last;
            }
            return sql;
        }
        return sql;
    }

    private static String swapOr(String sql) {
        int index = sql.lastIndexOf("order by");
        if (index >= 0) {
            String pre = sql.substring(0, index);
            String last = sql.substring(index);//order by id where id = 3
            int where = last.indexOf("or");
            sql = pre;
            if (where >= 0) {
                //       where id = 3                    order by id
                sql += last.substring(where) + " " + last.substring(0, where);
            } else {
                sql += last;
            }
            return sql;
        }
        return sql;
    }
    public static String removeAfterOrder(String sql) {
        int index = sql.lastIndexOf("order by");
        if (index >= 0) {
            int i = sql.indexOf(" where ", index);
            if (i < 0) {
                i = sql.indexOf(" and ", index);
                if (i < 0) {
                    i = sql.indexOf(" or ", index);
                }
            }
            if (i >= 0) {
                return sql.substring(0, i);
            }
        }
        return sql;
    }

}
