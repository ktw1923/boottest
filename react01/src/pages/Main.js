import React from "react";
import { Link } from "react-router-dom";
import IndexLayout from "../layout/IndexLayout";

function Main(props) {

    return (
        <IndexLayout>
            <div>
                <Link to={'/middle'}>Middle</Link>  {/* 리액트 내부에서 컴포넌트 처리
                                                    suspense와 lazy로 필요시점에 컴포넌트 로딩*/}
                <div className="text-2xl">
                    <div>Main</div>
                </div>
            </div>
        </IndexLayout>
    );
}

export default Main;