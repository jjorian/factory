package ir.bokaeyan.factory.respository

import ir.bokaeyan.factory.api.BaseDataSource
import ir.bokaeyan.factory.api.RetrofitApi


class FactoryRespository: BaseDataSource()
 {
     companion object
     {
         private var respository: FactoryRespository? = null
         @Synchronized
         fun getInstance(): FactoryRespository
         {
             if (respository == null)
             {
                 if (respository == null)
                 {
                     respository = FactoryRespository()
                 }
             }
             return respository as FactoryRespository
         }
     }
      fun AddData( uid: Int,did: Int)=getResult{ RetrofitApi.create().GetDeviceUrl(uid,did)}


 }