import styled from "@emotion/styled";
import { FC } from "react";
import { colors } from "../../design-token";

interface Props {
  onChange: () => void;
  items: string[];
  title: string;
  name: string;
}
const RadioGroup: FC<Props> = ({ onChange, items, name, title }) => {
  const renderItem = (item: string, idx: number) => {
    return (
      <label className="radio" key={idx}>
        <input type="radio" onChange={onChange} name={name} value={item} />
        <div className="radio-text">{item}</div>
      </label>
    );
  };
  return (
    <RadioGroupStyle>
      <div className="title">{title}</div>
      <div className="radio-group">{items.map(renderItem)}</div>
    </RadioGroupStyle>
  );
};

export default RadioGroup;

const RadioGroupStyle = styled.div`
  & .title {
    color: #394659;
    font-size: 16px;
    font-weight: 700;
    margin-bottom: 24px;
  }
  & .radio {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    &-text {
      color: ${colors.description};
      font-size: 14px;
      margin-top: 12px;
    }
    & input {
      margin: 0;
    }
  }
  & .radio-group {
    display: flex;
    justify-content: space-around;
  }
`;
