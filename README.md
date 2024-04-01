#LFI-Striker

LFI Striker is a powerful framework designed to detect and exploit Local File Inclusion (LFI) vulnerabilities effortlessly, providing security professionals with a streamlined approach to identifying and exploiting these critical security flaws.

> LFI, short for Local File Inclusion, represents a critical vulnerability that allows attackers to include and execute arbitrary files on a target server, potentially leading to unauthorized access and sensitive data exposure.

## Summary

* [Modules](#modules)
* [Install](#install)
* [Examples](#examples)
* [Payloads](#payloads)

## Modules

  _       _____   ___           ____    _            _   _
 | |     |  ___| |_ _|         / ___|  | |_   _ __  (_) | | __   ___
 | |     | |_     | |   _____  \___ \  | __| | '__| | | | |/ /  / _ \
 | |___  |  _|    | |  |_____|  ___) | | |_  | |    | | |   <  |  __/
 |_____| |_|     |___|         |____/   \__| |_|    |_| |_|\_\  \___|


							https://github.com/MouathA
usage: java -jar LFI-Striker.jar -i <input_file_path> [-d <delay_time_ms>]
 `-d,--delay <arg>`   Delay time in milliseconds
 `-i,--input <arg>`   Input file path

## Install

Basic install from releases

[!https://github.com/MouathA/LFI-Strike/releases]


## Examples

>First you need to download subfinder and gau for get all domains and parameter
 
```powershell
[!] subfinder -d example.com | gau >> domains.txt

[!] java -jar LFI-Strike.jar -i domains.txt 

[!] java -jar LFI-Strike.jar -i domains.txt -d 1000

```

## Payloads

> https://raw.githubusercontent.com/capture0x/LFI-FINDER/main/lfi.txt







