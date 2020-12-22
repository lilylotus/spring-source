/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction.testfixture;

import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
@SuppressWarnings("serial")
public class CallCountingTransactionManager extends AbstractPlatformTransactionManager {

	public TransactionDefinition lastDefinition;
	public int begun;
	public int commits;
	public int rollbacks;
	public int inflight;

	public TX tx;


    @Override
	protected Object doGetTransaction() {
	    if (tx == null) {
            tx = new TX(false);
        }
        System.out.println("doGetTransaction [" + tx.hashCode() + "]");
		return tx;
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
        System.out.println("doBegin transaction [" + transaction.hashCode() + "] definition[" + definition.hashCode() + "] tx [" + tx.hashCode() + "]");
		this.lastDefinition = definition;
		++begun;
		++inflight;

		tx.hasTx = true;
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
        System.out.println("doCommit status [" + status.hashCode() + "] tx [" + tx.hashCode() + "]");
		++commits;
		--inflight;
	}

    @Override
    protected boolean isExistingTransaction(Object transaction) throws TransactionException {
        return ((TX) transaction).hasTx;
    }

    @Override
	protected void doRollback(DefaultTransactionStatus status) {
        System.out.println("doRollback status [" + status.hashCode() + "] tx [" + tx.hashCode() + "]");
		++rollbacks;
		--inflight;
	}

	public void clear() {
		begun = commits = rollbacks = inflight = 0;
	}

    @Override
    public String toString() {
        return "{" +
                "lastDefinition=" + lastDefinition +
                ", begun=" + begun +
                ", commits=" + commits +
                ", rollbacks=" + rollbacks +
                ", inflight=" + inflight +
                ", tx=" + tx.hashCode() +
                '}';
    }



    static class TX implements SavepointManager {
	    private boolean hasTx;

        public TX(boolean hasTx) {
            this.hasTx = hasTx;
        }

        public boolean isHasTx() {
            return hasTx;
        }

        public void setHasTx(boolean hasTx) {
            this.hasTx = hasTx;
        }

        @Override
        public Object createSavepoint() throws TransactionException {
            System.out.println("createSavepoint");
            return new Object();
        }

        @Override
        public void rollbackToSavepoint(Object savepoint) throws TransactionException {
            System.out.println("rollbackToSavepoint");
        }

        @Override
        public void releaseSavepoint(Object savepoint) throws TransactionException {
            System.out.println("releaseSavepoint");
        }
    }
}
