// Включение редактирования поля
function enableEditing(button) {
    const form = button.closest('.profile-form');
    const inputs = form.querySelectorAll('.form-input');
    const editBtn = form.querySelector('.edit-btn');
    const saveBtn = form.querySelector('.save-btn');
    const cancelBtn = form.querySelector('.cancel-btn');

    inputs.forEach(input => input.disabled = false);
    editBtn.style.display = 'none';
    saveBtn.style.display = 'inline-block';
    cancelBtn.style.display = 'inline-block';
}

// Отмена редактирования
function cancelEditing(button) {
    const form = button.closest('.profile-form');
    const inputs = form.querySelectorAll('.form-input');
    const editBtn = form.querySelector('.edit-btn');
    const saveBtn = form.querySelector('.save-btn');
    const cancelBtn = form.querySelector('.cancel-btn');

    inputs.forEach(input => {
        input.disabled = true;
        if (input.type !== 'password') {
            input.value = input.defaultValue;
        } else {
            input.value = '';
        }
    });

    editBtn.style.display = 'inline-block';
    saveBtn.style.display = 'none';
    cancelBtn.style.display = 'none';
}

// Отправка письма для подтверждения email
function sendVerificationEmail() {
    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/profile/send-verification-email', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        }
    })
        .then(response => {
            if (response.ok) {
                alert('Письмо с подтверждением отправлено на ваш email!');
            } else {
                alert('Ошибка при отправке письма. Попробуйте позже.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Произошла ошибка. Попробуйте позже.');
        });
}

// Обработка изменения аватара
document.getElementById('avatarInput').addEventListener('change', function() {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('avatarImage').src = e.target.result;
            document.querySelector('.save-avatar-btn').style.display = 'inline-block';
        }
        reader.readAsDataURL(file);
    }
});