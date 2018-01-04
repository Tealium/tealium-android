# Tealium Crash Reporter Module


**Table of Contents**

- [Introduction](#introduction)
- [How Tealium can help](#how-tealium-can-help)
- [Dependencies](#dependencies)
- [Instructions](#instructions)
  - [Deleting the stored AdIdentifer information (not usually required)](#deleting-the-stored-adidentifer-information--not-usually-required-)
  - [Caveats and warnings](#caveats-and-warnings)

## Introduction

Apps crash! Get to know the who, what, when, and why your app is crashing. 

## How Tealium can help

Add the new Crash Reporter module to better understand why and what may be causing your app to crash. The module collect crash information such as thread data and stack trace. 

## Dependencies

This module has no external dependencies, and requires no additional permissions.

## Instructions
### Option 1: Manually add dependency
Download the AdIdentifier file from 

[]()

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
    compile(name:'tealium-5.4.0', ext:'aar') // only required if you do not already have this reference
    compile(name:'tealium.crashreporter-5.4.0', ext:'aar')
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
    compile 'com.tealium:library:5.4.0' //only required if you do not have this reference
    compile 'com.tealium:crashreporter:1.0'
}
```

In the same block where you instantiate the Tealium library (after instantiating Tealium), add the following calls:

```
// import the Tealium Crash Reportor module in the import section
import com.tealium.crashreporter.CrashReporter;
…
String tealiumInstanceId = "sampleInstance"
boolean isStackTrackTruncated = true;

Tealium.Config config = Tealium.Config.create(this, "tealium", "demo", "dev");
CrashReporter.initialize(tealiumInstanceId, config, isStackTrackTruncated);

Tealium instance = Tealium.createInstance(tealiumInstanceId, config);
```

### Crash Event Tracking

Once initialized, the Crash Reporter module will automatically capture crash data and trigger a track call when a thread abruptly terminates and invokes the `.uncaughtExceptionHandler()` due to an uncaught exception. 


### Caveats and warnings

1. The current implementaion is supports a singleton object. This may change in future implementations.
