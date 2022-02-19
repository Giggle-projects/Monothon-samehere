import styled from "@emotion/styled";
import { FC, HTMLAttributes } from "react";
import { colors } from "../../design-token";
import donwArrowIcon from "../../static/svg/downArrowIcon.svg";

interface Props extends HTMLAttributes<HTMLSelectElement> {
  title: string;
  items: string[];
  name: string;
}
const DropDown: FC<Props> = ({ title, items, placeholder, ...restProps }) => {
  return (
    <DropDownStyle>
      <div className="title">{title}</div>
      <select className="drop-down" {...restProps}>
        <option value="" selected hidden>
          {placeholder}
        </option>
        {items.map((item, idx) => (
          <option key={idx} value={item}>
            {item}
          </option>
        ))}
      </select>
    </DropDownStyle>
  );
};

export default DropDown;

const DropDownStyle = styled.div`
  width: 100%;
  & .title {
    color: ${colors.title};
    font-size: 16px;
    font-weight: 700;
    margin-bottom: 10px;
  }
  & .drop-down {
    appearance: none;
    width: 100%;
    border: 1px solid ${colors.line};
    height: 45px;
    display: flex;
    align-items: center;
    background: url(${donwArrowIcon}) no-repeat right 9px center;
    border-radius: 8px;
    padding: 0 20px;
  }
`;
