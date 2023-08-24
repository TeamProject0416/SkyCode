const searchOption = document.querySelector('.searchOption'),
            optionList = document.querySelector('.optionList');

        let onOff = 0;
        searchOption.addEventListener('click', (e) => {
            e.preventDefault();
            if (onOff === 0) {
                optionList.style.top = '50px';
                optionList.style.display = 'block';
                onOff = 1;
            } else if (onOff === 1) {
                optionList.style.top = '0px';
                optionList.style.display = 'none';
                onOff = 0;
            }
        })