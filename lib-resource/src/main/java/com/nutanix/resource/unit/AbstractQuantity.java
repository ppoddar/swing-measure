package com.nutanix.resource.unit;

import com.nutanix.resource.Quantity;
import com.nutanix.resource.Resource;
import com.nutanix.resource.Unit;

public abstract class AbstractQuantity implements Quantity {
	private final Resource.Kind kind;
	private final double value;
	private final Unit unit;
	private final boolean isIntegral;

	public AbstractQuantity(Resource.Kind kind, double amount, Unit unit) {
		this(kind, amount, unit, true);
	}

	protected AbstractQuantity(Resource.Kind kind, double amount, Unit unit, boolean integral) {
		this.kind = kind;
		this.value = amount;
		this.unit = unit;
		this.isIntegral = integral;
	}

	public int compareTo(Quantity o) {
		if (o.getKind() != this.getKind()) {
			throw new IllegalArgumentException();
		}
		Quantity q2 = o.convert(this.unit);
		if (this.value > q2.getValue()) {
			return 1;
		} else if (this.value < q2.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}

	public Resource.Kind getKind() {
		return kind;
	}

	public double getValue() {
		return value;
	}

	public Unit getUnit() {
		return unit;
	}

	@Override
	public Quantity plus(Quantity other) {
		assertSameKind(this, other);
		Quantity added = other.convert(this.getUnit());
		double a = this.value + added.getValue();
		return this.clone(a, this.unit);
	}

	public Quantity minus(Quantity other) {
		assertSameKind(this, other);
		Quantity taken = other.convert(this.getUnit());
		double a = this.value - taken.getValue();
		if (a < 0) {
			throw new IllegalArgumentException("can not subtract larger quantity [" + other + "]"
					+ " from smaller quantity [" + this + "]");
		}
		return this.clone(a, this.unit);
	}

	public Quantity convert(Unit to) {
		double f = to.getConversionFactor(this.unit);
		return this.clone(f * value, to);
	}
	
	public Quantity convert() {
		Unit highest = getKind().getHighestUnit();
		return (highest == null) ? this : convert(highest);
	}


	@Override
	public double fraction(Quantity other) {
		assertSameKind(this, other);
		double f = other.getUnit().getConversionFactor(this.unit);
		return f * other.getValue() / this.value;
	}

	@Override
	public boolean isIntegral() {
		return isIntegral;
	}

	public String toString() {
		if (isIntegral) {
			return String.format("%s:%d %s", getKind(), (int) getValue(), getUnit());
		} else {
			return String.format("%s:%f %s", getKind(), getValue(), getUnit());
		}
	}
	
	void assertSameKind(Quantity c1, Quantity c2) {
		if (c1.getKind() != c2.getKind()) {
			throw new IllegalArgumentException("different kind of capacity " 
					+ c1.getKind() + " and " +  c2.getKind());
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!Quantity.class.isInstance(obj)) return false; 
		Quantity other = Quantity.class.cast(obj);
		
		if (other.getKind() != this.getKind()) {
			return false;
		}
		Quantity c2 = other.convert(this.getUnit());
		return c2.getValue() == this.getValue();
	}

	@Override
	public Quantity times(int n) {
		return this.clone(value * n, this.unit);
	}

	

}
