package flow;

public class Pair<T> {
	public T u;
	public T v;
	
	public Pair(T u, T v) {
		this.u = u;
		this.v = v;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((u == null) ? 0 : u.hashCode());
		result = prime * result + ((v == null) ? 0 : v.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<?> other = (Pair<?>) obj;
		if (u == null) {
			if (other.u != null) {
				return false;
			}
		} else if (!u.equals(other.u)) {
			return false;
		}
		if (v == null) {
			if (other.v != null) {
				return false;
			}
		} else if (!v.equals(other.v)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", u, v);
	}

	
}

