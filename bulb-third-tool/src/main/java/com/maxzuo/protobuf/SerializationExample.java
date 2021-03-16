package com.maxzuo.protobuf;


/**
 * Protocol Buffer 序列化和反序列化
 * <p>
 * Created by zfh on 2019/08/23
 */
public class SerializationExample {

    public static void main(String[] args) {
        NetworkEntity.Company.Builder companyBuilder = NetworkEntity.Company.newBuilder().setId(1).setType(2).setSummary("Fish Ball");
        NetworkEntity.Company company  = companyBuilder.build();

        // Modify value
        company = company.toBuilder().setId(3).setType(4).build();
        System.out.println(company.getId());
        System.out.println(company.getType());
        System.out.println(company.getSummary());
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
