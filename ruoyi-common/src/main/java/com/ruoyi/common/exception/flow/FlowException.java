package com.ruoyi.common.exception.flow;

import com.ruoyi.common.exception.BaseException;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
public class FlowException extends CustomException
{
    public FlowException(String msg, Object[] args, Integer code)
    {
        super(MessageUtils.message(msg, args), code);
    }

    public static class SubTskNotCompletedException extends FlowException {
        public SubTskNotCompletedException() {
            super("flow.subtsk.complete",new String[0], 1031);
        }
    }

    public static class DeleteNotRunningException extends FlowException {
        public DeleteNotRunningException() {
            super("flow.delete.notrunning",new String[0], 1100);
        }
    }
}
