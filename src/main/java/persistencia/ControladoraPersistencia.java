
package persistencia;

import logica.Usuario;

public class ControladoraPersistencia {
    
   
    
    HorarioJpaController horaJPA = new HorarioJpaController();
    OdontologoJpaController odontoJPA = new OdontologoJpaController();
    PacienteJpaController pacJPA = new PacienteJpaController();
    PersonaJpaController persJPA = new PersonaJpaController();
    ResponsableJpaController respJPA = new ResponsableJpaController();
    SecretaioJpaController secreJPA = new SecretaioJpaController();
    TurnoJpaController turnJPA = new TurnoJpaController();
    UsuarioJpaController usuJPA = new UsuarioJpaController();
    
    public ControladoraPersistencia() {
        
    }

    public void crearUsuario(Usuario usu) {
        usuJPA.create(usu);
    }
    
}
