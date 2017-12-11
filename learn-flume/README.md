# flume-ng

## 安装, 配置环境变量

安装文件目录中的　`conf` 目录下，有些样板文件．


## 配置

source, sink, channel

## flume-ng


* flume-ng version

* flume-ng agent


一个　｀agent｀ 需要定义　`sources, channels, sinks`．

```
配置格式：
    
    agent-name.sources = source-name, source-name
    agent-name.channels = channel-names
    agent-name.sinks = sink-names
    
    agent-name.sources.source-name.type = type-name
    agent-name.sources.source-name.channels = channel-names
    
    agent-name.sinks.sink-name.type = sink-type
    agent-name.sinks.sink-name.chanel = channel-name
    
    agent-name.channels.channel-name.type = channel-type
    agent-namae.channels.channel-name.capcity = capcity
```

## avro rpc

