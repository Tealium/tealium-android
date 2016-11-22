# Tealium Android Ad Identifier Module


**Table of Contents**

- [Introduction](#introduction)
- [How Tealium can help](#how-tealium-can-help)
- [Dependencies](#dependencies)
- [Instructions](#instructions)
  - [Deleting the stored AdIdentifer information (not usually required)](#deleting-the-stored-adidentifer-information--not-usually-required-)
  - [Caveats and warnings](#caveats-and-warnings)

## Introduction

The Android operating system provides each device with a user-resettable "Ad Identifier" (a.k.a. ADID) that can be used to identify an individual device's interaction with adverts displayed via ad networks, and can help companies to measure how effective their advertising spend has been.

## How Tealium can help

To take the burden away from your app developers, Tealium has built a module that integrates with our standard Android library (version 5.0+), which automatically collects the ad identifier when available, and makes the data available as a standard variable for you to use with your tags.

## Dependencies

This module depends on the Google Play Services Ads SDK, and will not function if the SDK is not present in your app. It was built against SDK version 9.4.0, but should function with older and newer versions without issue.

## Instructions
Download the AdIdentifier file from 

[https://github.com/Tealium/tealium-android/tree/master/Support/AdIdentifier]()

Include in your app's "libs" directory, and add the following to your build.gradle:

```
dependencies {
    compile(name:'tealium-5.0.4', ext:'aar') // only required if you do not already have this reference
    compile(name:'TealiumAdIdentifier', ext:'aar')
    compile(name:'tealium.lifecycle-5.0.4', ext:'aar') // only required if you do not already have this reference and require lifecycle tracking
}
```

In the project's build.gradle, make sure you have the following:

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
In the same block where you instantiate the Tealium library (after instantiating Tealium), add the following calls:

```
// import the Tealium AdIdentifier module in the import section
import com.tealium.adidentifier.TealiumAdIdentifier;
// context to pass to AdIdentifier module
Context mContext = this;
…
// substitute the example instance name here with the same instance name you used when initializing the Tealium library
private static final String TEALIUM_INSTANCENAME = "teal";
// call this to store the referrer as Persistent data - recommended
TealiumAdIdentifier.setIdPersistent(TEALIUM_INSTANCENAME, mContext);
// call this to store the referrer as Volatile data (reset at app restart/terminate) - not recommended in most cases
TealiumAdIdentifier.setIdVolatile(TEALIUM_INSTANCENAME, mContext);
```

### Deleting the stored AdIdentifer information (not usually required)

To delete the stored Ad Identifier string, you can call the standard methods provided by the Tealium library to remove variables from persistent or volatile storage. The key name for the variable is exposed by the TealiumAdIdentifier class. Here's an example to remove the stored Ad Identifier from both persistent and volatile data. This code will have no effect and will not throw an error if the variable doesn't already exist.

```
// import the Tealium AdIdentifier module in the import section
import com.tealium.adidentifier.TealiumAdIdentifier;
…
private static final String TEALIUM_INSTANCENAME = "teal";
final Tealium instance = Tealium.getInstance(TEALIUM_INSTANCENAME);
// remove from persistent data store
instance.getDataSources().getPersistentDataSources().edit().remove(TealiumAdIdentifier.KEYNAME).apply();

// remove from volatile data store (session only)
instance.getDataSources().getVolatileDataSources().remove(TealiumAdIdentifier.KEYNAME);

```

### Caveats and warnings

1. If the user has not allowed access to the Ad Identifier, the module will set the "google_adid" variable to the string "Ad Tracking Disabled". You should handle this in your Tealium IQ/AudienceStream configuration to ensure you pass the correct/expected data to your 3rd party technology vendors.

2. Please ensure you have already included the Google Play Services Ads SDK in your app. See here for instructions: [https://developers.google.com/android/guides/setup](https://developers.google.com/android/guides/setup)