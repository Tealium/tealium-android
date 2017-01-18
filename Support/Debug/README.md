# Tealium Android Debug

The ```tealium.debugmodule-5.x.aar``` included in this directory depends on the ```tealium-5.x.aar```.

## How to get started

After integrating the Tealium Android Library into an applicaiton, add the 'com.tealium:debug:1.0' dependency into your app gradle file.

To enable, call:

```java
String tealiumInstanceId = ...
Tealium.Config config = ...

DebugModule.setupDebugModule(config);
Tealium.createInstance(tealiumInstanceId, config;
```

### Set a Debug queue limit

The module will queue items if it is not connected to the Debug Page. Once connection is established, the queue is emptied and all subsequent events will automatically display on the browswer. The default queue limit is 100 and has a MAX_INT of 1000.

To set custom queue limit, call: 

```java
Tealium.Config config = ...
config.setOverrideDebugQueueMaxLimit(200);

DebugModule.setupDebugModule(config);
Tealium.createInstance(tealiumInstanceId, config;
```