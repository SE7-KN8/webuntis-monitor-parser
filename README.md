# webuntis-monitor-parser
Simple program to export webuntis monitor data to file or mysql

# Usage
1. Download the latest version from [Releases](https://github.com/SE7-KN8/webuntis-monitor-parser/releases)
2. Install java 8 (if you havn't already)
3. Create default config via: `java -jar [name of the downloaded jar] [path to the config(e.g. "default.properties" or "/home/conf/conf.properties")] [file|sql]`
4. Example command: `java -jar webuntis-monitor-parser.jar default.properties sql`
5. Edit the created *.properties file. Be sure to escape `:` and `=` with `\:` and `\=`
6. Execute the program with just the config path: `java -jar [name of the downloaded jar] [path to the config]`
7. See the results
