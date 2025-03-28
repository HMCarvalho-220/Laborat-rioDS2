import { useState } from 'react';
import axios from 'axios';
import './App.css';

const App = () => {
  // Estado para os dados do formulário
  const [formData, setFormData] = useState({
    nome: '',
    cpf: '',
    email: '',
    telefone: '',
    endereco: ''
  });

  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const validateForm = () => {
    const newErrors = {};
    
    if (!formData.nome.trim()) newErrors.nome = 'Nome é obrigatório';
    if (!formData.cpf) newErrors.cpf = 'CPF é obrigatório';
    if (!formData.email.includes('@')) newErrors.email = 'Email inválido';
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) return;
    
    setIsSubmitting(true);
    
    try {
      await axios.post('http://localhost:8080/api/clientes', formData);
      alert('Cliente cadastrado com sucesso!');
      // Limpa o formulário após cadastro
      setFormData({
        nome: '',
        cpf: '',
        email: '',
        telefone: '',
        endereco: ''
      });
    } catch (error) {
      console.error('Erro ao cadastrar:', error);
      alert('Erro ao cadastrar cliente');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Cadastro de Clientes</h1>
        
        <form onSubmit={handleSubmit} className="cadastro-form">
          <div className="form-group">
            <label>Nome Completo</label>
            <input
              type="text"
              name="nome"
              value={formData.nome}
              onChange={handleChange}
              placeholder="Digite o nome completo"
              required
            />
            {errors.nome && <span className="error">{errors.nome}</span>}
          </div>

          <div className="form-group">
            <label>CPF</label>
            <input
              type="text"
              name="cpf"
              value={formData.cpf}
              onChange={handleChange}
              placeholder="000.000.000-00"
              required
            />
            {errors.cpf && <span className="error">{errors.cpf}</span>}
          </div>

          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="seu@email.com"
              required
            />
            {errors.email && <span className="error">{errors.email}</span>}
          </div>

          <div className="form-group">
            <label>Telefone</label>
            <input
              type="tel"
              name="telefone"
              value={formData.telefone}
              onChange={handleChange}
              placeholder="(00) 00000-0000"
            />
          </div>

          <div className="form-group">
            <label>Endereço</label>
            <input
              type="text"
              name="endereco"
              value={formData.endereco}
              onChange={handleChange}
              placeholder="Rua, Número, Bairro"
            />
          </div>

          <button 
            type="submit" 
            disabled={isSubmitting}
            className="submit-btn"
          >
            {isSubmitting ? 'Cadastrando...' : 'Cadastrar Cliente'}
          </button>
        </form>
      </header>
    </div>
  );
};

export default App;