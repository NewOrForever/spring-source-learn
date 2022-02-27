package org.example.transactionSynchronization;

import org.springframework.transaction.support.TransactionSynchronization;

/**
 * ClassName:TransactionSynchronization
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/22 16:24
 * @Author:qs@1.com
 */
public class MyTransactionSynchronization implements TransactionSynchronization {
        @Override
        public void suspend() {
            System.out.println("test被挂起了");
        }

        @Override
        public void resume() {
            System.out.println("test被恢复了");
        }

        @Override
        public void beforeCommit(boolean readOnly) {
            System.out.println("test准备提交了");
        }

        @Override
        public void beforeCompletion() {
            System.out.println("test准备要提交或回滚了");
        }

        @Override
        public void afterCommit() {
            System.out.println("test提交成功了");
        }

        @Override
        public void afterCompletion(int status) {
            System.out.println("test提交或回滚成功了");
        }
}
