package ir.bokaeyan.factory.respository

import ir.bokaeyan.factory.api.BaseDataSource
import ir.bokaeyan.factory.api.RetrofitApi


class UserRespository: BaseDataSource()
 {
     companion object
     {
         private var userRespository: UserRespository? = null
         @Synchronized
         fun getInstance(): UserRespository
         {
             if (userRespository == null)
             {
                 if (userRespository == null)
                 {
                     userRespository = UserRespository()
                 }
             }
             return userRespository as UserRespository
         }
     }
      fun signIn( username: String,password: String)=getResult{ RetrofitApi.create().Login(username,password)}


 }