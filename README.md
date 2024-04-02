#LFI-Striker

LFI Striker is a powerful framework designed to detect and exploit Local File Inclusion (LFI) vulnerabilities effortlessly, providing security professionals with a streamlined approach to identifying and exploiting these critical security flaws.

> LFI, short for Local File Inclusion, represents a critical vulnerability that allows attackers to include and execute arbitrary files on a target server, potentially leading to unauthorized access and sensitive data exposure.

## Summary

* [Modules](#modules)
* [Install](#install)
* [Examples](#examples)
* [Payloads](#payloads)
* [Recommended](#recommended)

## Modules
```
  _       _____   ___           ____    _            _   _
 | |     |  ___| |_ _|         / ___|  | |_   _ __  (_) | | __   ___
 | |     | |_     | |   _____  \___ \  | __| | '__| | | | |/ /  / _ \
 | |___  |  _|    | |  |_____|  ___) | | |_  | |    | | |   <  |  __/
 |_____| |_|     |___|         |____/   \__| |_|    |_| |_|\_\  \___|


							https://github.com/MouathA
usage: java -jar LFI-Striker.jar -i <input_file_path> [-d <delay_time_ms>]
 `-d,--delay <arg>`   Delay time in milliseconds
 `-i,--input <arg>`   Input file path
```
## Install

Basic install from releases

You can download the latest release of ParamFinder from the [GitHub repository](https://github.com/MouathA/LFI-Striker/releases).


## Examples

> Initially, you'll need to download [ParamFinder](https://github.com/MouathA/ParamFinder/releases) to obtain all domains and parameters for scanning.
 
```powershell

[!] java -jar LFI-Strike.jar -i domains.txt 

[!] java -jar LFI-Strike.jar -i domains.txt -d 1000

```

![Start](https://github.com/MouathA/LFI-Striker/assets/103407967/9c6fdc19-091a-4c52-87de-7297d7409642)


![Screenshot_1](https://github.com/MouathA/LFI-Striker/assets/103407967/4b2bd7ff-ac15-4f54-acf6-4f01afff42a4)

## Payloads

> Payloads from -- > https://raw.githubusercontent.com/capture0x/LFI-FINDER/main/lfi.txt

## Recommended

> Use Java 11 to avoid errors.





