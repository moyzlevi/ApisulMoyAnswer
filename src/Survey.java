
public class Survey {
	private Integer andar;
	private Character turno;
	private Character elevador;
	
	public int getAndar() {
		return andar;
	}
	public Survey(Integer andar, Character turno, Character elevador) {
		this.andar = andar;
		this.turno = turno;
		this.elevador = elevador;
	}
	@Override
	public String toString() {
		return "Survey [andar=" + andar + ", turno=" + turno + ", elevador=" + elevador + "]";
	}
	public void setAndar(int andar) {
		this.andar = andar;
	}
	public char getTurno() {
		return turno;
	}
	public void setTurno(char turno) {
		this.turno = turno;
	}
	public char getElevador() {
		return elevador;
	}
	public void setElevador(char elevador) {
		this.elevador = elevador;
	}
	
	
}
