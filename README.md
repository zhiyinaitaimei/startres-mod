# Startres Mod

一个为整合包开发者设计的轻量级工具，通过 KubeJS 实现：
- 玩家视角绑定：将视角锁定到实体或自定义摄像机
- 化身战斗系统：召唤物自动协助作战（原为我的整合包召唤物流派核心功能）
- 可扩展性：兼容原版/模组生物，支持标签控制 AI 模式（需手动开启）

> 💡 本模组最初服务于我的未发布整合包，因功能通用性而开源。高级功能（如AI模式切换）默认关闭，避免干扰简单使用。

快速使用 kubejs服务端中:
ItemEvents.rightClicked('minecraft:golden_sword', event => {
    let entity = event.level.createEntity("minecraft:zombified_piglin");
    entity.addTag("incarnation");
    entity.addTag("incarnation_owner_" + event.player.getUuid());
    entity.potionEffects.add('startres:incarnation_life', 30 * 20, 0);
    entity.spawn();
}); //召唤一个持续30秒的僵尸猪灵
