
# QuickFixPractise #

# Abstract 概述 #
This practise helps you to set up basic acceptor(server) and initiator(client) using QuickFIX APIs. For detailed background and explanations, please refer to below pages.

这篇实践将帮助你基于QuickFIX搭建基本的消息收发端。详细的教程和背景知识参考以下博客内容：

1. QuickFix Java 讲解（一）概述、下载方法，和协议内容：[https://blog.csdn.net/zongziczz/article/details/108565222](https://blog.csdn.net/zongziczz/article/details/108565222)
1. QuickFix Java 讲解（二）搭建框架、解决依赖：[https://blog.csdn.net/zongziczz/article/details/108589057](https://blog.csdn.net/zongziczz/article/details/108589057)
1. QuickFix Java 讲解（三）客户端的搭建与解析：[https://blog.csdn.net/zongziczz/article/details/108652566](https://blog.csdn.net/zongziczz/article/details/108652566)

# How to Play 怎么运行 #

## Project Description 项目介绍 ##

There are two projects within this repository: FixAcceptor and FixInitiator. 

- FixAcceptor acts like a server which receives messages from multi clients, and reply back to them;
- FixInitiator acts like a client which sends out order messages, and get reports from the server.

本仓库下面有两个项目：FixAcceptor 和 FixInitiator.

- FixAcceptor 的作用是服务器端，它收取来自客户端的消息，并给予反馈。
- FixInitiator 的作用是客户端，它向服务器端发出订阅请求，并收取回报。

## User Manual 用户手册 ##

When openning these two projects, rememnber to modify "src/main/resources/quickfix.properties" for below settings:

- SocketConnectHost=XXX.XXX.XXX.XXX (Your IP for conmunication)
- SocketConnectPort=XXX (Your port for conmunication)

Others could remain unchanged, but when you change, remember to update both sides, so they could be synced!

打开两个项目以后，记得更改"src/main/resources/quickfix.properties"文件中的如下设置：

- SocketConnectHost=XXX.XXX.XXX.XXX (通信的IP地址，一般取你本地的)
- SocketConnectPort=XXX (通信的端口，不要跟别的冲突就行)

其他设置可以先不管，但如果你要改的话，记得两边都要改，不然对不上。

# Contact 联系方式 #

- Blog：[https://me.csdn.net/zongziczz](https://me.csdn.net/zongziczz)
- Email：[zongzhe_chen@sina.com](mailto:zongzhe_chen@sina.com)











