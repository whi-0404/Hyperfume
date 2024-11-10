import { memo } from 'react';

const homePage = () => {
    return (
        <>
            <h1>Header</h1>
            <h1>Body</h1>
            <h1>Footer</h1>
        </>);
};

export default memo(homePage);