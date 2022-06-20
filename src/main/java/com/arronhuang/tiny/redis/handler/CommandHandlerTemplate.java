package com.arronhuang.tiny.redis.handler;

import com.arronhuang.tiny.redis.enums.CommandEnum;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class CommandHandlerTemplate {

    /**
     * 请求真实处理方法, 由子类根据具体命令完成
     *
     * @param args
     * @return
     */
    public abstract RespResponse doHandle(List<String> args);

    /**
     * 检查参数合法性, 由子类根据具体命令完成
     *
     * @param args
     */
    public abstract void checkArgs(List<String> args);

    /**
     * 根据命令名称, 进行参数数量检查
     *
     * @param commandName 命令名称
     * @param args        参数列表
     */
    protected void checkArgQty(String commandName, List<String> args) {
        CommandEnum commandEnum = RequestHandlerFactory.getCommandEnum(commandName);
        int argQty = commandEnum.getArity();
        if (argQty > 0) {
            AssertUtil.sizeEquals(args, argQty - 1);
        } else if (argQty == 0) {
            AssertUtil.sizeEquals(args, argQty);
        } else {
            AssertUtil.sizeMoreThan(args, argQty * -1 - 1);
        }
    }

    /**
     * 请求处理模板, 包含参数校验, 请求实际处理等动作
     *
     * @param request
     * @return
     */
    public RespResponse handle(RespRequest request) {
        String commandName = request.getCommandName();
        List<String> args = request.getArgs();
        RespResponse response;

        try {
            checkArgQty(commandName, args);
            checkArgs(args);
            response = doHandle(args);
        } catch (Exception e) {
            response = RespResponse.error(e.getMessage());
            log.error("request process error: {}", request, e);
        }

        return response;
    }

}
