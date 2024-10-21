document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevenir el envío del formulario por defecto

    const customerData = {
        firstName: document.getElementById('firstName').value,
        surname: document.getElementById('surname').value,
        email: document.getElementById('email').value,
        birthDate: document.getElementById('birthDate').value,
        password: document.getElementById('password').value,
    };

    fetch('/api/customers/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(customerData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta de la red');
            }
            return response.json();
        })
        .then(data => {
            console.log('Cliente registrado:', data);
            alert('Registro exitoso!');
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Error al registrar al cliente.');
        });
});
