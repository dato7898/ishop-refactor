package net.devstudy.framework.factory;

import java.util.LinkedList;
import java.util.List;

import net.devstudy.framework.FrameworkSystemException;

public class TransactionSynchronizationManager {
	private static final ThreadLocal<List<TransactionSynchronization>> transactionSynchronization = new ThreadLocal<>();

	public static void addSynchronization(TransactionSynchronization transactionSynchronization) {
		List<TransactionSynchronization> list = getSynchronization();
		if (list == null) {
			throw new FrameworkSystemException(
					"transactionSynchronization is null. Does your service method have @Transactional(readOnly=false) annotation?");
		}
		list.add(transactionSynchronization);
	}

	static void initSynchronization() {
		transactionSynchronization.set(new LinkedList<>());
	}

	static List<TransactionSynchronization> getSynchronization() {
		return transactionSynchronization.get();
	}

	static void clearSynchronization() {
		transactionSynchronization.remove();
	}
}
