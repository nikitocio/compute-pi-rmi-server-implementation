package com.server.impl;

import com.server.interfaces.Compute;
import com.server.interfaces.Task;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author nivanov, <a href="mailto:Nikita.Ivanov@returnonintelligence.com">Ivanov Nikita</a>
 * @since 01-Oct-17
 */
public class ComputeImpl implements Compute {

	public ComputeImpl() {
		super();
	}

	public <T> T computeTask(Task<T> task) throws RemoteException {
		return task.execute();
	}

	public static void main(String[] args){
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		rebindRemoteObjectInRegistry(1099);
		rebindRemoteObjectInRegistry(1100);
		rebindRemoteObjectInRegistry(1101);
	}

	private static void rebindRemoteObjectInRegistry(int port){
		Compute stub = null;
		try {
			stub = (Compute) UnicastRemoteObject.exportObject(new ComputeImpl(), port);
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind("Compute", stub);
			System.out.println("Compute was rebinded in registry with port: " + port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
