package com.nutanix.capacity;


public class Memory extends AbstractQuantity implements Quantity {

	public Memory(double amount, MemoryUnit unit) {
		super(ResourceKind.MEMORY, amount, unit);
	}
	public Memory(double amount, String symbol) {
		super(ResourceKind.MEMORY, amount, 
			  ResourceKind.MEMORY.getUnit(symbol));
	}

	@Override
	public Quantity clone(double amount, Unit unit) {
		return new Memory(amount, (MemoryUnit)unit);
	}
	@Override
	public Unit getPreferredUnit() {
		return MemoryUnit.MB;
	}

}
