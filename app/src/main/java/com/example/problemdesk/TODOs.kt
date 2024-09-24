package com.example.problemdesk

//TODO 1.8 - themes, custom styles, colors string resources
//TODO 1.9 - final design

//TODO data storage?

// usecases?
//TODO    REMEMBER ME
//TODO need to add an exit button in profile?

//TODO final redesign

//TODO 4 - firebase, pushs...   +  manager UI, graphs       --- they works, but maybe i should check that:

//getInProgressIssue() - 1
//getCompletedIssue() - 2
//getDeniedIssue() - 3
//// Master
//getRequestsForMaster() - 4
//getRequestsForMasterMonitor() - 5
//// Executor
//executorUnassignRequest() - 6
//executorMyTasksRequest() - 7
//это по желанию, если приходит пуш и
//ты на него кликаешь, то внутри есть инфа какой запрос дернуть чтоб обновить ui с новыми данными

//TODO manager ui

//TODO add a empty lists placeholders
//TODO loading animation
//TODO drag refresh gesture

//TODO update bottom nav icons and design

//TODO need to check all for following MVVM, Clean Arch and SOLID   ---!!!

//errors (from okhttp?)
//errors (шаблоны на определенные ошибки? (нет интернета, мертвый сервер)
//чек на связь с интернетом

//shared prefs для удобства ---?
//setupobservers+click listeners
//
//диалоги
//
//принятие заявки на выполнение (повторение сообщения, отсутствие обновления)
//
//цвета в статусах
//логи в фрагменты
//
//обновление жестом
//datastorage
//секурити
//string resourses
//
//тест флоу
//тест ролей
//тест пушей
//
//анимация обновления
//токен рефреш

//TODO диалоги с обсерверами        -------------------------!!!!!!!!!!!!!!!!!!!!!!!
//диалоги с обсерверами странно реализованы - зачем нужен succesStatus и errorStatus,
// если все это можно (и нужно) объединить?

//singleLiveEvent...

//прикол с выдвиганием bottomsheet?

//TODO микролаги при переходе с фрагмента в фрагмент. с чем связанно? тяжелый интерфейс? сеть? потоки?
// на 13 ведре полет нормальный

//----------------------

//перекинуть все в main activity?
//TODO remember me
//TODO fcm refresh
//TODO user's inputs should be remembered through app destroy??

//last task date bug (no data)

//обновление при закрытии bottomSheet?

//че с пушами?

//TODO че то с потоками не то!
//2024-09-09 21:01:10.846 11782-11782 Choreographer           com.example.problemdesk              I
//Skipped 1 frames!  The application may be doing too much work on its main thread.


//TODO смена темы баг (на ведре 13 полет нормальный, на моем ведре все крашится)
//TODO общие алерт диалоги??
//TODO progressDialog???
//TODO забыли пароль (пока скрыть)


//check shared prefs clearing when log out (log.i)

//logout button after logout

//check working from 2 smartphones
//loading

//логи (okhttp?)

//специализация юзера в профайле

//надо отлавливать ошибки из OkHttp, а не из логов. логи вообще удалить можно

//-----------------------------------------------------
//TODO обязательно для бетки 1.1


//TODO fcm refresh test
//TODO https migration

//TODO push test
//TODO access token refresh
//TODO remember me test (it may be not working when access token dies???)
//TODO закрытие ботом шита не всегда запускает загрузку
//TODO логи с okhttp в диалогах ошибок (подумать над реализацией диалогов)
//TODO landscape mode!!!! ---- test on tablets
//TODO тест UI

//TODO тесты


//-----------------------------------------------------

//----------------------
//TODO чек разбора

//по гиту
// .idea
// .editorconfig
//скрины, иконка //android art generator
//
//
//
//

//TODO сохранение данных заявки в офлайне (в будущем можно сделать отложенную доставку как в мессенджерах)

//Графики:
//UI графика
//тест на больших данных
//заглушка с пустыми данными
//
//Рейтинг:
//
//Токены:
//access - (заход в прил будучи залогиненным, но при протухшем токене)
//
//_________________________________________________
//
//Мелочь:
//цвета заявок
//логин загрузка (диалог, загушка)
//
//Пуши:
//тест пушей - вроде работает
//тест fcm - вроде работает

//----------------------