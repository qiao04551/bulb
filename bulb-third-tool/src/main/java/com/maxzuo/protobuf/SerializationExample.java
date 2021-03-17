package com.maxzuo.protobuf;


/**
 * Protocol Buffer 序列化和反序列化
 * <p>
 * Created by zfh on 2019/08/23
 */
public class SerializationExample {

    public static void main(String[] args) {
        NetworkEntity.Employee employee = NetworkEntity.Employee.newBuilder().setName("mars").setAge(25).build();
        NetworkEntity.Company.Builder companyBuilder =
                NetworkEntity.Company.newBuilder().setId(1).setType(2).setSummary("Fish Ball").addEmployees(employee);
        NetworkEntity.Company company  = companyBuilder.build();

        // Modify value
        NetworkEntity.Employee cathy = NetworkEntity.Employee.newBuilder().setName("cathy").setAge(25).build();
        company = company.toBuilder().setId(3).setType(4).addEmployees(cathy).build();
        System.out.printf("%d, %d, %d, %s\n",
                company.getId(), company.getType(), company.getEmployeesCount(), company.getSummary());
    }

    private void descrialize() {
        NetworkEntity.Company company = NetworkEntity.Company.newBuilder().setId(1).setType(2).build();

        byte[] data = company.toByteArray();

        try {
            NetworkEntity.Company descCompany = NetworkEntity.Company.parseFrom(data);
            System.out.println(descCompany);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
