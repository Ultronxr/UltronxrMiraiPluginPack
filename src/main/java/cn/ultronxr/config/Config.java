//package cn.ultronxr.config;
//
//import net.mamoe.mirai.console.data.Value;
//import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static net.mamoe.mirai.console.data.PluginDataKt.value;
//import net.mamoe.mirai.console.data.*;
//import net.mamoe.mirai.console.internal.data.cast;
//import net.mamoe.mirai.console.internal.data.setValueBySerializer;
//import net.mamoe.mirai.console.internal.data.valueImpl;
//import net.mamoe.mirai.console.plugin.jvm.JvmPlugin;
//import net.mamoe.mirai.console.util.JavaFriendlyApi;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import kotlin.reflect.KClass;
//import kotlin.reflect.KType;
//import kotlin.reflect.KTypeProjection;
//import kotlin.reflect.KVariance;
//import kotlin.reflect.full.createType;
///**
// * @author Ultronxr
// * @date 2022/06/29 10:44
// */
//public class Config extends JavaAutoSavePluginConfig {
//    public static final Config INSTANCE = new Config("config.yml");
//
//    public Config(@NotNull String saveName) {
//        super(saveName);
//    }
//
//
//    public final Value<String> string = value("test"); // 默认值 "test"
//
//    public final Value<List<String>> list = typedValue(createKType(List.class, createKType(String.class))); // 无默认值, 自动创建空 List
//
//    public final Value<Map<Long, Object>> custom = typedValue(
//            createKType(Map.class, createKType(Long.class), createKType(Object.class)),
//            new HashMap<Long, Object>() {{ // 带默认值
//                put(123L, "ok");
//            }}
//    );
//
//
//}
