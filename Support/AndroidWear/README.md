## Brief 

The libraries in this directory are for integrating Tealium tracking into Android Wear.

## How to get started

### Mobile/Handheld Side 

After integrating the Tealium android library into an application, add the ```tealium.mobile-5.x.x.aar``` to that application's dependencies as well. No additional code is necessary. 

### Wear Side

In the application's wear module add the ```tealium.wear-5.x.x.aar``` dependency. Subclass the Wear Application's subclass, and add the TealumWear's initialization code in its ```onCreate``` function: 

```java
public class WearApp extends Application {

    public static final String TEALIUM_MAIN = "main";

    @Override
    public void onCreate() {
        super.onCreate();

        TealiumWear.createInstance(
                TEALIUM_MAIN,
                TealiumWear.Config.create(this)
                        .setLogLevel(Log.VERBOSE));
    }
```

To track views: 

```java
final DataMap data = new DataMap();
// ...

TealiumWear.getInstance(WearApp.TEALIUM_MAIN)
    .trackView("Main Watch View", data);
}
```

To track events: 

```java
final DataMap data = new DataMap();
// ...

TealiumWear.getInstance(WearApp.TEALIUM_MAIN)
    .trackEvent("Main Button Click", data);
```