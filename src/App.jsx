import { useState } from 'react';
import './App.css';

const USUARIOS = [
  { email: 'usuario@test.com', password: '123', rol: 'USER', nombre: 'Usuario' },
  { email: 'admin@test.com', password: '123', rol: 'ADMIN', nombre: 'Admin' },
];

export default function App() {
  const [usuarioActual, setUsuarioActual] = useState(null);
  const [error, setError] = useState('');
  const [exito, setExito] = useState('');
  const [prestamos, setPrestamos] = useState([
    { id: 1, email: 'usuario@test.com', monto: 1000, estado: 'pendiente' },
    { id: 2, email: 'usuario@test.com', monto: 2000, estado: 'aprobado' },
    { id: 3, email: 'usuario@test.com', monto: 30000, estado: 'pendiente' },
  ]);
  const [monto, setMonto] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    const email = e.target.email.value;
    const password = e.target.password.value;
    const user = USUARIOS.find(u => u.email === email && u.password === password);
    if (user) {
      setUsuarioActual(user);
      setError('');
    } else {
      setError('Credenciales incorrectas');
    }
  };

  const handleLogout = () => setUsuarioActual(null);

  const handleSolicitar = (e) => {
    e.preventDefault();
    const montoNum = Number(monto);
    if (!monto || montoNum <= 0) {
      setError('El monto debe ser mayor a 0');
      return;
    }
    const nuevo = {
      id: prestamos.length + 1,
      email: usuarioActual.email,
      monto: montoNum,
      estado: 'pendiente',
    };
    setPrestamos([...prestamos, nuevo]);
    setMonto('');
    setError('');
    setExito('✅ Préstamo solicitado correctamente');
    setTimeout(() => setExito(''), 3000);
  };

  const cambiarEstado = (id, nuevoEstado) => {
    setPrestamos(prestamos.map(p => p.id === id ? { ...p, estado: nuevoEstado } : p));
  };

  if (!usuarioActual) {
    return (
      <div className="container">
        <div className="card">
          <h2>Iniciar Sesión</h2>
          <p className="subtitle">Ingrese sus credenciales para acceder</p>
          <form onSubmit={handleLogin}>
            <label>Email</label>
            <input name="email" type="email" placeholder="Email" required />
            <label>Contraseña</label>
            <input name="password" type="password" placeholder="Contraseña" required />
            {error && <p className="error">{error}</p>}
            <button type="submit">Ingresar</button>
          </form>
        </div>
      </div>
    );
  }

  if (usuarioActual.rol === 'USER') {
    const misPrestamos = prestamos.filter(p => p.email === usuarioActual.email);
    return (
      <div className="container">
        <div className="header">
          <h2>Bienvenido, {usuarioActual.nombre}</h2>
          <button onClick={handleLogout}>Cerrar Sesión</button>
        </div>

        <div className="card">
          <h3>Solicitar Préstamo</h3>
          <p className="subtitle">Ingrese el monto que desea solicitar</p>
          <form onSubmit={handleSolicitar}>
            <label>Monto</label>
            <input
              type="number"
              placeholder="Monto"
              min="1"
              value={monto}
              onChange={(e) => setMonto(e.target.value)}
              required
            />
            {error && <p className="error">{error}</p>}
            {exito && <p className="exito">{exito}</p>}
            <button type="submit">Solicitar</button>
          </form>
        </div>

        <div className="card">
          <h3>Mis Préstamos</h3>
          {misPrestamos.map(p => (
            <p key={p.id}>
              Monto: ${p.monto} - Estado: <span className={p.estado}>{p.estado}</span>
            </p>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="header">
        <h2>Hola, {usuarioActual.nombre}</h2>
        <button onClick={handleLogout}>Cerrar Sesión</button>
      </div>

      <div className="card">
        <h3>Gestionar Solicitudes de Préstamos</h3>
        {prestamos.map(p => (
          <div key={p.id} className="prestamo-item">
            <p>Usuario: {p.email} - Monto: ${p.monto} - Estado: <span className={p.estado}>{p.estado}</span></p>
            {p.estado === 'pendiente' && (
              <div className="acciones">
                <button className="aprobar" onClick={() => cambiarEstado(p.id, 'aprobado')}>Aprobar</button>
                <button className="rechazar" onClick={() => cambiarEstado(p.id, 'rechazado')}>Rechazar</button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}