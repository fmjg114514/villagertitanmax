# VillagerTitan Fabric 版（MC 26.3）

村民泰坦 + 女巫泰坦，Fabric 26.3 / Loader 0.20.1 / Fabric API 0.165.0+26.3 / GeckoLib 5.5.7。

## 环境说明

沙盒无法完成编译（只有 Java 11 运行时、无 javac / Gradle，且 Maven 网络被拦截），因此交付的是**完整源码**，请在你自己的电脑上编译。

编译要求：
- **JDK 25**（MC 26.3 必须，Java 8/17/21 不行）
- 能联网（Gradle 需要下载 Minecraft、Fabric Loom、Fabric API、GeckoLib）

## 编译步骤

```bash
cd VillagerTitanFabric
./gradlew build
```

产物：`build/libs/villagertitan-1.0.0.jar`

Windows 用 `gradlew.bat build`。

## 安装

把以下 4 个 jar 一起放进 `mods` 文件夹（手机 FCL 则是它自己的 mods 目录）：

1. `villagertitan-1.0.0.jar`（本项目编译产物）
2. `fabric-api-0.165.0+26.3.jar`
3. `geckolib-fabric-26.3-5.5.7.jar`（如果你装的不是 5.5.7，把 `build.gradle` 里的 `geckolib_version` 改成你的版本）
4. Fabric Loader 0.20.1（由启动器安装，不是 jar）

## 召唤

```
/summon villagertitan:villager_titan
/summon villagertitan:witch_titan
```

村民泰坦生成时会在脚下建造 30x30 黑曜石平台并常加载该区块。

## 已实现

| 项目 | 说明 |
|---|---|
| 村民泰坦 | 20 倍体型、红色雷击（粒子光柱 + 秒杀伤害）、免疫一切伤害、生成平台 |
| 女巫泰坦 | 20 倍体型、**碰撞箱 0**、免疫伤害、无法删除、常加载保证 tick 不停止、无近战 |
| 女巫远程 | 巨大药水瓶，命中爆炸 + 紫色雷击 + 全部负面效果（**不含失明**），满级（255） |
| 女巫大招 | 每 10 秒（200 tick）从天召唤 100 个巨大药水瓶 |
| 血雨 | 已取消 |
| 动作 | 使用僵尸泰坦优化动作（idle2 / walk2 / attack / cast2 / emerge2） |

## 注意

- 女巫泰坦碰撞箱为 0，**你无法用近战打到它**，这是需求本身决定的。
- 两个泰坦都免疫伤害且无法删除；如需移除，请用 `/kill` 之外的强制手段或直接删存档实体。
- GeckoLib 资源路径按 GeckoLib 5 规范放在 `assets/villagertitan/geckolib/...`。
