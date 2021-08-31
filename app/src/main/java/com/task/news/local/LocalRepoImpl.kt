package com.task.news.local

import com.task.news.local.persistance.DAO
import com.task.news.local.preference.PreferencesManager
import com.task.news.model.prefsModel.FilterModel
import com.task.news.utils.constant.PrefsKeys
import javax.inject.Inject

/*
    Class which responsible to invoke all methods from Room Database , DataStore and API Services
 */
class LocalRepoImpl @Inject constructor(
        private val myDao: DAO,
        private val prefManager: PreferencesManager
): LocalRepository
{

    override fun returnBoolean(key: String): Boolean{
        return prefManager.returnBoolean(key)
    }

    override fun saveBoolean(key: String , success: Boolean){
        return prefManager.saveBoolean(key , success)
    }


    override fun clearPrefs() = prefManager.clearSharedPreferences()
    override fun saveInt(key: String, value: Int) {
        prefManager.saveInt(key , value)
    }

    override fun returnInt(key: String): Int {
        return prefManager.returnInt(key)
    }

    override fun saveString(key: String, value: String) {
        prefManager.saveString(key, value)
    }

    override fun returnString(key: String): String {
        return prefManager.returnString(key)
    }

    override fun submitSelectionProcess(filterModel: FilterModel) {
        prefManager.apply {
            saveObject(PrefsKeys.FILTER_MODEL , filterModel)
            saveBoolean(PrefsKeys.ON_BOARDING_SELECTION , true)
        }
    }

    override fun fetchFilterModel(): FilterModel {
        return prefManager.getObject(PrefsKeys.FILTER_MODEL , FilterModel::class.java)
    }


}