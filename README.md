# Startres Mod

一个为整合包开发者设计的轻量级工具，通过 KubeJS 实现：
- 玩家视角绑定：将视角锁定到实体或自定义相机
- 化身战斗系统：召唤物自动协助作战（原为我的整合包召唤物流派核心功能）
- 可扩展性：兼容原版/模组生物，shift+右键化身实体控制 AI 模式（需在配置文件开启,默认关闭）

> 💡 本模组最初服务于我的未发布整合包，因功能通用性而开源。部分功能（如AI模式切换）默认关闭，避免干扰简单使用。
>
> 🌟 核心功能
1. 摄像机视角绑定
通过 KubeJS 将玩家视角绑定到任意实体（模组提供了一个专门用于绑定玩家视角的相机实体）。

2. 化身战斗系统   
拥有标签: incarnation 和 incarnation_owner_<ownerUUID> 的实体自动协助玩家作战。   
支持仇恨继承、目标优先级控制，兼容大部分原版及模组生物。   
提供 Buff 控制化身持续时间，结束时自动消失。   

🚀 快速开始   
Forge 47.4.0+   
minecraft 1.20.1   
下载 startres-x.x.x.jar 放入 mods 文件夹。   
在 KubeJS 脚本中调用。   

⚠️ 注意事项
少量生物可能因原生 AI 冲突攻击玩家，需手动调整。

📚 详细文档
- [中文 Wiki](https://github.com/zhiyinaitaimei/Startres-Mod/wiki/zh_首页)
- [English Wiki](https://github.com/zhiyinaitaimei/Startres-Mod/wiki/en_Home)  

