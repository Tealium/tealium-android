# Tealium Install Referrer Module


**Table of Contents**

- [Tealium Install Referrer Module](#tealium-install-referrer-module)
	- [Introduction](#introdution)
	- [How Tealium can help](#how-tealium-can-help)
	- [Dependencies](#dependencies)
	- [Instructions](#instructions)
		- [Deleting the stored InstallReferrer information (not usually required)](#deleting-the-stored-installreferrer-information-not-usually-required)
		- [Sample formatted referrer string](#sample-formatted-referrer-string)
		- [Caveats and warnings](#caveats-and-warnings)
	- [Example code snippet for Tealium SDK v4](#example-code-snippet-for-tealium-sdk-v4)

## Introduction

A common problem with mobile apps is trying to figure out how your effective marketing spend for acquisition is. For example, you may spend money with ad networks for promoting your app, but have no idea how many of these ads leads to successful download and opening. Uniquely on Android, the operating system provides a handy way to identify where the user downloaded your app from, and this information can be gathered by your app and passed to any 3rd party analytics tool.

## How Tealium can help

To take the burden away from your app developers, Tealium has built a module that integrates with our standard Android library (version 5.2+), which automatically collects the referrer information when available, and makes the data available as a standard variable for you to use with your tags.

## Dependencies

This module has no external dependencies, and requires no additional permissions.

## Instructions
### Option 1: Manually add dependency
Download the tealium.installreferrer-5.2.0.aar file from 

[https://github.com/Tealium/tealium-android/tree/master/Support/InstallReferrer]()

In the project's root build.gradle, make sure you have the following:

```
allprojects {
    repositories {
       jcenter()
        flatDir {
                dirs 'libs'
        }
    }
}
```

Include in your app's "libs" directory, and add the following to your project module's build.gradle:

```
dependencies {
    compile(name:'tealium-5.2.0', ext:'aar') // only required if you do not already have this reference
    compile(name:'tealium.installreferrer-5.2.0', ext:'aar')
    compile(name:'tealium.lifecycle-5.0.4', ext:'aar') // only required if you do not already have this reference and require lifecycle tracking
}
```


### Option 2: Maven Dependency
In your project's root gradle file, add the following maven repository:

```
maven {
    url "http://maven.tealiumiq.com/android/releases/"
}
```

In your project module gradle file, add the following maven dependency:

```
dependencies{
    compile 'com.tealium:library:5.2.0' //only required if you do not have this reference
    compile 'com.tealium:installreferrer:1.0'
    compile 'com.tealium:lifecycle:1.1' //only required if you do not already have this reference and require lifecycle tracking
}
```

In the same block where you instantiate the Tealium library (after instantiating Tealium), add the following calls:

```
// import the Tealium InstallReferrer module in the import section
import com.tealium.installreferrer.InstallReferrerReceiver;
…
// substitute the example instance name here with the same instance name you used when initializing the Tealium library
private static final String TEALIUM_INSTANCENAME = "teal";
// call this to store the referrer as Persistent data - recommended
InstallReferrerReceiver.setReferrerPersistent(TEALIUM_INSTANCENAME);
// call this to store the referrer as Volatile data (reset at app restart/terminate) - not recommended in most cases
InstallReferrerReceiver.setReferrerVolatile(TEALIUM_INSTANCENAME);
```

### Deleting the stored InstallReferrer information (not usually required)

To delete the stored install referrer string, you can call the standard methods provided by the Tealium library to remove variables from persistent or volatile storage. The key name for the variable is exposed by the InstallReferrerReceiver class. Here's an example to remove the stored install referrer from both persistent and volatile data. This code will have no effect and will not throw an error, if the variable doesn't already exist.

```
// import the Tealium InstallReferrer module in the import section
import com.tealium.installreferrer.InstallReferrerReceiver;
…
private static final String TEALIUM_INSTANCENAME = "teal";
final Tealium instance = Tealium.getInstance(TEALIUM_INSTANCENAME);
// remove from persistent data store
instance.getDataSources().getPersistentDataSources().edit().remove(InstallReferrerReceiver.KEYNAME).apply();

// remove from volatile data store (session only)
instance.getDataSources().getVolatileDataSources().remove(InstallReferrerReceiver.KEYNAME);

```

### Sample formatted referrer string

You can expect to receive the following URL-formatted data in the INSTALL\_REFERRER variable, assuming that your incoming URL contained all the specified campaign information.

```utm_source=test_source&
utm_medium=test_medium&utm_term=test_term&
utm_content=test_content&
utm_campaign=test_name

```

### Caveats and warnings

Please be aware that the Tealium InstallReferrer module implements a BroadcastReceiver, specifically for the INSTALL\_REFERRER broadcast. This broadcast is only sent once on first launch of the app, and the Android OS will only call the FIRST BroadcastReceiver for this particular broadcast, if you define more than 1 BroadcastReceiver. Thus, if you have other 3rd party SDKs that handle the INSTALL\_REFERRER also, you will need to implement a "proxy" BroadcastReceiver that calls the onReceive method of each individual receiver for the INSTALL\_REFERRER broadcast.

## Example code snippet for Tealium SDK v4

This module is only compatible with Tealium SDK v5.X.

If you are still using v4.X and require this functionality, please see below for a code example

```
// Android Manifest 
<receiver
    android:name="com.tealium.InstallReferrerReceiver"
    android:exported="true">
    <intent-filter>
        <action android:name="com.android.vending.INSTALL\_REFERRER" />
    </intent-filter>
</receiver>
```
 
```
// Class for retrieving install referrer
public class InstallReferrerReceiver extends BroadcastReceiver {

    private static final String INSTALL\_REFERRER = "INSTALL\_REFERRER";

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        String referrer = extras.getString("referrer");

        if (StringUtils.isNotBlank(referrer)) {
            Tealium.getGlobalCustomData().edit().putString(INSTALL\_REFERRER, referrer).apply();
        }
    }
}
```