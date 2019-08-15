# Tealium LifeCycle Tracking

The ```tealium.lifecycle-5.x.aar``` included in this directory depends on the ```tealium-5.x.aar```.

The module populates the following data sources for mapping:

* lifecycle_dayofweek_local
* lifecycle_dayssincelaunch
* lifecycle_dayssincelastwake
* lifecycle_dayssinceupdate
* lifecycle_diddetectcrash
* lifecycle_firstlaunchdate
* lifecycle_firstlaunchdate_MMDDYYYY
* lifecycle_hourofday_local
* lifecycle_isfirstlaunch
* lifecycle_isfirstlaunchupdate
* lifecycle_isfirstwakemonth
* lifecycle_isfirstwaketoday
* lifecycle_lastlaunchdate
* lifecycle_lastsleepdate
* lifecycle_lastwakedate
* lifecycle_launchcount
* lifecycle_secondsawake
* lifecycle_priorsecondsawake
* lifecycle_sleepcount
* lifecycle_totallaunchcount
* lifecycle_totalsecondsawake
* lifecycle_totalsleepcount
* lifecycle_totalwakecount
* lifecycle_type
* lifecycle_updatelaunchdate
* lifecycle_wakecount

## Events

The module tracks 3 lifecycle events: 

* launch
* sleep
* wake

These events are the possible values of the ```lifecycle_type``` data source.

## Automatic Tracking

```Receiver```s, ```Service```s, and ```Content Provider```s can boot an application without presenting a UI; consequently, a user isn't "using" the application when an application is started this way. Tealium LifeCycle tracking solely associates usage with the ```Activity``` lifecycle. 

The library leverages the [Application.ActivityLifecycleCallbacks](http://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks.html) API, and evaluates its conditions on  [onActivityResumed](http://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks.html#onActivityResumed%28android.app.Activity%29) and [onActivityPaused](http://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks.html#onActivityPaused%28android.app.Activity%29). 

To enable, call:

```java
String tealiumInstanceId = ...
Tealium.Config config = ...
boolean isAutoTracking = true;

LifeCycle.setupInstance(tealiumInstanceId, config, isAutoTracking);
Tealium.createInstance(tealiumInstanceId, config);
```

### Launch Event

Generated on the first ```onActivityResumed``` to occur. If ```onActivityPaused``` is called before ```onActivityResumed``` (when the library is initialized in the middle of the ```Activity``` lifecycle), then the module creation time is used to generate a ```launch``` event.

### Sleep Event

Generated 5 seconds after ```onActivityPaused``` is called and ```onActivityResumed``` was not called. This indicates that the application has been backgrounded because a new view has not been presented.

### Wake Event

Generated during ```onActivityResumed``` when more than 5 seconds has elapsed since the last ```onActivityPaused```. This indicates that ```onActivityResumed``` is called because the app is foregrounding and not because of a view change.

## Manual Tracking

**ADVANCED: Not recommended**

Available for specific lifecycle paradigms and non-conventional lifecycle tracking. To enable, call:

```java
Tealium.Config config = ...
boolean isAutoTracking = false;

LifeCycle.setupInstance(TEALIUM_INSTANCE_ID, config, isAutoTracking);
Tealium.createInstance(TEALIUM_INSTANCE_ID, config);
```

> ```Tealium``` and the attached ```LifeCycle``` multitons are remote-killable by publish settings in TealiumIQ. Null checks should be performed when getting the instances to call their methods.

### Launch Event

To manually track a ```launch``` event, call [trackLaunchEvent](http://tealium.github.io/tealium-android/com/tealium/lifecycle/LifeCycle.html#trackLaunchEvent(java.util.Map)).

```java
LifeCycle lifeCycle = LifeCycle.getInstance(TEALIUM_INSTANCE_ID);

if(lifeCycle != null) {
	
	// OPTIONAL
	Map<String, String> data = new HashMap<>();
	data.put("boot_time", "5ms");
	
	lifeCycle.trackLaunchEvent(data);
}
```

### Sleep Event

To manually track a ```sleep``` event, call [trackSleepEvent](http://tealium.github.io/tealium-android/com/tealium/lifecycle/LifeCycle.html#trackSleepEvent(java.util.Map)).

```java
LifeCycle lifeCycle = LifeCycle.getInstance(TEALIUM_INSTANCE_ID);

if(lifeCycle != null) {
	
	// OPTIONAL
	Map<String, String> data = new HashMap<>();
	data.put("foreground_time", "4000ms");
	
	lifeCycle.trackSleepEvent(data);
}
```

### Wake Event

To manually track a ```wake``` event, call [trackWakeEvent](http://tealium.github.io/tealium-android/com/tealium/lifecycle/LifeCycle.html#trackWakeEvent(java.util.Map)).

```java
LifeCycle lifeCycle = LifeCycle.getInstance(TEALIUM_INSTANCE_ID);

if(lifeCycle != null) {
	
	// OPTIONAL
	Map<String, String> data = new HashMap<>();
	data.put("background_time", "600000ms");
	
	lifeCycle.trackWakeEvent(data);
}
```

## Crash Tracking

When a ```launch``` event follows a ```wake``` event or vice-versa, this indicates a crash has occurred since the app did not successfully ```sleep```. If the aforementioned sequence occurs because of a Tealium library shutdown, this is not considered to be a crash event.

When a crash event is detected, the ```launch``` or ```wake``` event will have the ```lifecycle_diddetectcrash``` datasource will be added.

```javascript
{
	"lifecycle_type": "launch",
	"lifecycle_diddetectcrash":"true",
	//...
}
```






