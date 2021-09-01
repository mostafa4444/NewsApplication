# News Application ( News API - Hilt - Coroutines - MVVM - ROOM )

Base Section:
- This section have my base classes like BaseFragment and BaseViewModel to allow me provides all fragment easily and avoid duplicating code.

di Section:
- This section contains all my hilt modules which provides Retrofit, Preferences and Room and other fields and section.

Local Section:
- This section contains local storage implementation like Rooms and SharedPrefernce.

Model Section:
- Which contains my ui models and data class for request and response

Network Section:
- Which contain all remote implemenation and calling api interface also its contain generic coroutine class which responsible for handle all task with custom response archiricture to avoid duplicated implementation and handle remote task in Dispatchers and Jobs

UI Section:
- This section contain all my UI views for user like activities and fragments also contain the usecase for each api calls which injected into Activity or fragment view model and run api calls.


Utils Section: 
- This section contains Objects and some helper classes which used to provides Base url for api ,Animation Utils for app ,Checking network connection and Resource class which handle my ui states when calling api (Loading - Success - Empty Response - Server Error - Cancellation for Coroutines jobs)
