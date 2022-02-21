import DropDown from "../../components/common/DropDown";
import RadioGroup from "../../components/common/RadioGroup";
import Input from "../../components/common/Input";
import Button from "../../components/common/Button";

const SecondPage = (props) => {

  return (
      <>
        <div className="title">
          <>
            공유하고 싶은 정보를
            <br/> 자유롭게 입력해주세요!
            <div className="description">
              공유를 원하지 않는 정보는 비워두거나
              <br/> 항목을 삭제하시면 돼요!
            </div>
          </>
        </div>
        <div className="content">
          {props.additionalQuestions.map(question => {
            if (question.itemChoices.length > 6) {
              return <DropDown
                  title={question.name}
                  placeholder="선택해주세요."
                  name={question.name}
                  items={question.itemChoices}
                  onChange={props.onChangeAdditionalAnswer(question)}
              />
            }
            if (question.itemChoices.length > 0) {
              return <RadioGroup
                  title={question.name}
                  items={question.itemChoices}
                  name={question.name}
                  onChange={props.onChangeAdditionalAnswer(question)}
              />
            }
            return <Input
                title={question.name}
                value={props.additionalAnswers[question.id]}
                name={question.name}
                placeholder="입력해주세요."
                onChange={props.onChangeAdditionalAnswer(question)}
            />
          })}
        </div>
        <div className="bottomButton">
          <Button
              onClick={() => props.onSubmit()}
              disabled={props.loading}
          >
            프로필 작성 마치기
          </Button>
        </div>
      </>
  )
}

export default SecondPage;
