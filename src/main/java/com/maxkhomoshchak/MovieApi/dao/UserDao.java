package com.maxkhomoshchak.MovieApi.dao;


public class UserDao {
/*
    public boolean checkBasicInfo(User user){

        boolean flag = false;

        try(Connection connection = provider.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT user_id, nickname, password, email, is_vip FROM users WHERE nickname = ? OR email = ?");
        ){
            st.setString(1, user.getNickName());
            st.setString(2, user.getEMail());

            System.out.println((userMapper.mapList(st.executeQuery())).size());

            if((userMapper.mapList(st.executeQuery())).size() == 0){
                flag = true;
            }

        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }

        return flag;
    }*/
}
