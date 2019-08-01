package com.hulfman.codegenerator.util;

import com.google.common.base.Strings;
import com.hulfman.codegenerator.context.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 21:02
 */
public class MetaDataHelper
{
    private Properties properties;
    private Connection connection;
    private DatabaseMetaData metaData;

    public MetaDataHelper(Properties properties)
    {
        this.properties = properties;
        try
        {
            init();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void init() throws ClassNotFoundException, SQLException
    {
        String driverClassName = properties.getProperty("jdbc.driverClassName");
        String url = properties.getProperty("jdbc.url");
        String userName = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        if (Strings.isNullOrEmpty(driverClassName))
        {
            throw new IllegalArgumentException("jdbc.driverClassName not config");
        }
        if (Strings.isNullOrEmpty(url))
        {
            throw new IllegalArgumentException("jdbc.url not config");
        }
        if (Strings.isNullOrEmpty(userName))
        {
            throw new IllegalArgumentException("jdbc.username not config");
        }
        if (Strings.isNullOrEmpty(password))
        {
            throw new IllegalArgumentException("jdbc.password not config");
        }
        Class.forName(driverClassName);
        Properties props = new Properties();
        props.setProperty("user", userName);
        props.setProperty("password", password);
        props.setProperty("useInformationSchema", "true");
        connection = DriverManager.getConnection(url, props);
        metaData = connection.getMetaData();
    }

    public List<String> getTables() throws SQLException
    {
        List<String> tables = new ArrayList<>();
        ResultSet resultSet = metaData.getTables(connection.getCatalog(), "%", "%", new String[]{"TABLE"});
        while (resultSet.next())
        {
            String table = resultSet.getString("TABLE_NAME");
            tables.add(table);
        }
        return tables;
    }

    public String getTableRemark(String table) throws SQLException
    {
        ResultSet resultSet = metaData.getTables(connection.getCatalog(), "%", table, new String[]{"TABLE"});
        if (resultSet.next())
        {
            String remarks = resultSet.getString("REMARKS");
            return remarks;
        } else
        {
            return null;
        }
    }

    public List<Field> getFields(String tableName) throws SQLException
    {
        List<Field> fields = new ArrayList<>();
        ResultSet resultSet = metaData.getColumns(connection.getCatalog(), "%", tableName, "%");
        while (resultSet.next())
        {
            String columnName = resultSet.getString("COLUMN_NAME");
            CodeStyle codeStyle = new CodeStyle(columnName);
            String instanceName = codeStyle.toInstanceName();
            String resourceName = codeStyle.toResourceName();
            int digits = resultSet.getInt("DECIMAL_DIGITS");
            int columnType = resultSet.getInt("DATA_TYPE");
            String remark = resultSet.getString("REMARKS");
            String isNullable = resultSet.getString("IS_NULLABLE");
            String isAutoIncrement = resultSet.getString("IS_AUTOINCREMENT");
            String propertyType = getJavaType(columnType, digits);
            Field field = new Field();
            field.setColumnName(columnName);
            field.setColumnType(columnType);
            field.setPropertyType(propertyType);
            field.setPropertyName(instanceName);
            field.setResourceName(resourceName);
            field.parseRemark(remark);
            if ("NO".equalsIgnoreCase(isNullable))
            {
                field.setNotNull(true);
            }
            if ("YES".equalsIgnoreCase(isAutoIncrement))
            {
                field.setAutoIncrement(true);
            }
            field.determineHtmlType();
            if (!field.isIgnore())
            {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * translate database type into java type
     *
     * @param dbType
     * @return
     */
    private String getJavaType(int dbType, int digits)
    {
        String javaType;
        switch (dbType)
        {
            //12
            case Types.VARCHAR:
                javaType = "String";
                break;
            //4
            case Types.INTEGER:
                javaType = "Integer";
                break;
            //4
            case Types.SMALLINT:
                javaType = "Integer";
                break;
            //4
            case Types.TINYINT:
                javaType = "Integer";
                break;
            //-7
            case Types.BIT:
                javaType = "Integer";
                break;
            //-1
            case Types.LONGVARCHAR:
                javaType = "String";
                break;
            //-5
            case Types.BIGINT:
                javaType = "Long";
                break;
            //8
            case Types.DOUBLE:
                javaType = getPrecisionType();
                break;
            //7
            case Types.REAL:
                javaType = getPrecisionType();
                break;
            //6
            case Types.FLOAT:
                javaType = getPrecisionType();
                break;
            //3
            case Types.DECIMAL:
                javaType = "BigDecimal";
                break;
            //2
            case Types.NUMERIC:
                switch (digits)
                {
                    case 0:
                        javaType = "Integer";
                        break;
                    default:
                        javaType = getPrecisionType();
                }
                break;
            //91
            case Types.TIME:
                javaType = "Date";
                break;
            //91
            case Types.DATE:
                javaType = "Date";
                break;
            //93
            case Types.TIMESTAMP:
                javaType = "Date";
                break;
            default:
                javaType = "String";
        }
        return javaType;
    }

    private String getPrecisionType()
    {
        String dataType;
        if ("high".equals(properties.getProperty("precision")))
        {
            dataType = "BigDecimal";
        } else
        {
            dataType = "Double";
        }
        return dataType;
    }

    public void close()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

}
